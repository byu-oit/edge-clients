/*
 * Filename: ExceptionHandler
 * Created: Dec 12, 2008
 */
package edu.byu.framework.swing.exceptions;

import edu.byu.framework.swing.ByuSwingBootstrapper;
import edu.byu.framework.swing.util.DialogUtil;
import edu.byu.framework.swing.util.JOptionPaneWrapper;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Date;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentLinkedQueue;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

/**
 * @author tylers2
 */
public class ExceptionInternalHandler {

	private final static Logger LOG = Logger.getLogger(ExceptionInternalHandler.class);
	private final static Queue<ExceptionListener> listeners = new ConcurrentLinkedQueue<ExceptionListener>();
	private final static Queue<DebugAppender> appenders = new ConcurrentLinkedQueue<DebugAppender>();
	public final static String SUPRESS_DIALOG = "edu.byu.swing.exceptions.supressDialog";

	public static void addListener(ExceptionListener listener) {
		listeners.add(listener);
	}

	public static ExceptionListener[] getExceptionListeners() {
		return listeners.toArray(new ExceptionListener[listeners.size()]);
	}

	/**
	 * @param listener
	 * @return {@code true} if listener list changed
	 */
	public static boolean removeListener(ExceptionListener listener) {
		return listeners.remove(listener);
	}

	public static void addAppender(DebugAppender appender) {
		appenders.add(appender);
	}

	public static boolean removeAppender(DebugAppender appender) {
		return appenders.remove(appender);
	}

	public static DebugAppender[] getDebugAppenders() {
		return appenders.toArray(new DebugAppender[appenders.size()]);
	}

	public synchronized void handle(Throwable t) {
		doHandle(t);
	}

	public static synchronized void doHandle(Throwable t) {
		if (t instanceof OutOfMemoryError) {
			LOG.error("Out of Memory", t);
			showMessageDialog("Out of Memory");
			System.exit(-1);
		}

		try {
			String message = "Caught unknown exception: " + t.getMessage();
			LOG.error(message, t);
			logDebugInfo(t);
			takeScreenshot();
			synchronized (listeners) {
				for (ExceptionListener listener : listeners) {
					listener.handleException(t);
				}
			}
			//determine if the error is caused by a known issue (network timeout or JDBC update issue
			Throwable x = t;
			do {
				if (x instanceof java.net.SocketTimeoutException && x.getMessage().equals("Read timed out")) {
					message = "<html>A web service is non-responsive.<br/>Please wait a moment and try again. If this issue persists, please contact support.</html>";
					break;
				} else if (x instanceof org.apache.cxf.binding.soap.SoapFault && x.getMessage().contains("Could not execute JDBC batch update")) {
					String str = x.getMessage();
					int pos = str.indexOf("nested exception is");
					if (pos < 0) {
						break;
					}
					str = str.substring(pos + 19);
					pos = str.indexOf(":");
					str = str.substring(pos + 2);
					message = String.format("<html>An error occurred saving the data to the database. The error details are:<br/>%s</html>", str);
					break;
				} else if (x instanceof IndexOutOfBoundsException) {
					if (x.getCause() == null) {
						StackTraceElement[] stea = x.getStackTrace();
						if (stea != null && stea.length > 0) {
							if (stea[0].getClassName().equals("edu.byu.swing.models.ListTableModel") && stea[0].getMethodName().equals("getRow") && stea[0].getLineNumber() == 234 && stea[0].getFileName().equals("ListTableModel.java")) {
								message = "<html>A possible rendering error has occurred. Please check the screen for obviosly missing data (for example: not enough rows in a table). If there is something missing, please contact support. Otherwise, you should be able to ignore this message.</html>";
								break;
							}
						}
					}
				}
			} while ((x = x.getCause()) != null);

			LOG.debug("Showing exception dialog");
			showMessageDialog(message, t);
		} catch (Throwable tw) {
			String message = "Unrecoverable unhandled exception in exception listener.";
			LOG.error(message, tw);
			try {
				showMessageDialog(message, tw);
			} catch (Throwable t2) {
				LOG.error("Unable to show message box", t2);
			}
			LOG.error("Closing application");
			System.exit(-1);
		}
	}

	private static final String SCREENSHOT_DIR = "%1$s%2$sscreenshots%2$s";
	private static final String SCREENSHOT_FILE = "shot%1$ty%1$tb%1$td-%1$tH-%1$tM-%1$tS.png";

	private static void takeScreenshot() {
		if (shouldSuppressDialog()) {
			return;
		}
		Runnable r = new Runnable() {

			@Override
			public void run() {
				try {
					Rectangle bounds = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
					Robot robot = new Robot();

					BufferedImage bi = robot.createScreenCapture(bounds);

					String fileDir = String.format(SCREENSHOT_DIR,
							System.getProperty("java.io.tmpdir"),
							File.separator);
					File dir = new File(fileDir);
					dir.mkdirs();
					String fileName = String.format(SCREENSHOT_FILE, new Date());
					File screenshot = new File(dir, fileName);
					LOG.info(String.format("Writing screenshot to %s", screenshot.getAbsolutePath()));

					screenshot.createNewFile();
					ImageIO.write(
							bi,
							"png",
							screenshot);

					Thread.sleep(200);
				} catch (Exception ex) {
					LOG.error("Error taking screenshot", ex);
				}
			}
		};
		if (SwingUtilities.isEventDispatchThread()) {
			r.run();
		} else {
			try {
				SwingUtilities.invokeAndWait(r);
			} catch (Exception ex) {
				LOG.error("Error waiting for screenshot", ex);
			}
		}
	}

	private static void logDebugInfo(Throwable t) {
		StringBuilder sb = new StringBuilder("Debug information for exception\n---------------\n");
		addSystemInformation(sb);
		for (DebugAppender each : appenders) {
			CharSequence data = null;
			try {
				data = each.getDebugData(t);
			} catch (Exception ex) {
				LOG.error("Exception getting debug data", ex);
			}
			if (data == null) {
				continue;
			}
			sb.append(each.getDebugData(t));
			sb.append("\n---------------\n");
		}
		LOG.error(sb);
	}

	private static final Set<String> properties;

	static {
		properties = new TreeSet<String>();
		properties.add("java.home");
		properties.add("java.version");
		properties.add("user.name");
		properties.add("java.runtime.name");
		properties.add("path.separator");
		properties.add("jnlpx.heapsize");
		properties.add("java.io.tmpdir");
		properties.add("user.language");
		properties.add("java.vm.info");
		properties.add("sun.os.patch.level");
		properties.add("deployment.browser.vm.mozilla");
		properties.add("deployment.browser.vm.ie");
		properties.add("sun.desktop");
		properties.add("deployment.javaws.shortcut");
		properties.add("os.name");
		properties.add("java.vm.name");
		properties.add("sun.awt.exception.handler");
		properties.add("user.home");
		properties.add("java.vm.vendor");
		properties.add("user.dir");
		properties.add("jnlpx.vmargs");
		properties.add("os.arch");
		properties.add("javawebstart.version");
		properties.add("file.separator");
		properties.add("java.runtime.version");
		properties.add("java.vendor");
	}

	private static void addSystemInformation(StringBuilder sb) {
		for (String each : properties) {
			sb.append(String.format("%s = %s\n", each, System.getProperty(each)));
		}

		sb.append("\n\nUser Details:\n");
		sb.append(String.format("User netid: %s", ByuSwingBootstrapper.getUserNetId()));

		sb.append(sb);
	}

	private static void showMessageDialog(final String message) {
		JOptionPaneWrapper.showMessageDialog(null, message, "Application Error", JOptionPane.ERROR_MESSAGE);
	}

	private static boolean shouldSuppressDialog() {
		String prop = System.getProperty(SUPRESS_DIALOG);
		return prop != null && prop.equalsIgnoreCase("true");
	}

	public static void showMessageDialog(final String message, final Throwable t) throws Exception {
		if (shouldSuppressDialog()) {
			return;
		}
		ByuErrorPane.show(message, t);
	}
}
