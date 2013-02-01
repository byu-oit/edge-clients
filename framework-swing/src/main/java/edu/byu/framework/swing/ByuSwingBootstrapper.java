/*
 * ByuSwingBootstrapper.java
 *
 * Created on December 10, 2008
 */
package edu.byu.framework.swing;

import edu.byu.framework.swing.cas.CasAuthorizedClientLoginManager;
import edu.byu.framework.swing.cas.CasAuthorizedStatus;
import java.awt.event.ContainerEvent;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import edu.byu.framework.swing.exceptions.ExceptionHandlerImpl;
import edu.byu.framework.swing.exceptions.ExceptionInternalHandler;
import edu.byu.framework.swing.util.SplashScreenForm;
import edu.byu.framework.swing.util.SplashScreenListener;
import java.awt.*;
import java.awt.event.AWTEventListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ContainerListener;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.Arrays;
import java.util.Iterator;
import javax.swing.AbstractButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import static edu.byu.framework.swing.UserPrefs.setLAF;

/**
 * BYU Swing main application superclass.  Should be the superclass of all Swing
 * applications.<p>
 * Provides Spring context initialization, display and updating of a splash screen,
 * login/authorization functions, and utility methods for changing component state
 * on the EDT, getting Spring beans, and getting information on the current user.<p>
 * In order to use this class, simply inherit it, implement all abstract methods,
 * create a {@code main} method, create an instance, and call {@code run}.<p>
 * <h3>Example: (from the Honor Code Office application)</h3>
 * <pre>public static void main(String[] args) {
 *        honorcodeMain = new HonorcodeMain();
 *        honorcodeMain.run();
 *    }</pre>
 * <p>
 * Early naming conventions dictate that classes extending this are named with
 * the application name followed by "Main".  Ex: HonorcodeMain, TrafficMain
 * <p>
 * The window eventually called by this is generally called MainWindow.  Ex:
 * HonorcodeMainWindow, TrafficMainWindow
 *
 * @param <T> main application window type
 * @author tylers2
 * @author jmooreoa
 */
public abstract class ByuSwingBootstrapper<T extends Frame> extends SplashScreenForm {

	public static final String EVENT_LOG_NAME = "EventLog";
	public final Logger EVENT_LOG;
	private static boolean isInitialized = false;
	private static final Logger LOG = Logger.getLogger(ByuSwingBootstrapper.class);
	private static ApplicationContext appContext;

	/**
	 * Specifies locations of Spring context files
	 * @return array of context file locations
	 */
	protected abstract String[] getContextLocations();

	/**
	 * Returns the desired text color for the splash screen status messages.
	 * @return
	 */
	public abstract Color getSplashScreenForegroundColor();

	/**
	 * Specifies center point for splash screen message text.
	 * @return
	 */
	public abstract Point getMessageLocation();

	/**
	 *
	 * @return URL of splash screen image
	 */
	public abstract URL getSplashScreenURL();

	/**
	 * This will execute before initializing the Spring contexts
	 */
	public void setupBeforeSpring() {
	}

	public Rectangle getProgressBarBounds() {
		return null;
	}

	/**
	 * @see isAuthorized()
	 * Use isAuthorized instead
	 */
	@Deprecated
	protected boolean checkAuths() {
		return isAuthorized();
	}

	/**
	 * Checks if a person is authorized to run this application
	 * @return {@code false} if <b>NOT</b> authorized, {@code true} if authorized
	 */
	protected boolean isAuthorized() {
		return false;
	}

	/**
	 * Sets main window object
	 * @param window main window object for application
	 */
	protected abstract void setWindow(T window);
	/**
	 * Login manager for the application
	 */
	public static CasAuthorizedClientLoginManager loginManager;
	private final Class<T> windowClass;

	/**
	 * Creates a ByuSwingBootStrapper.  Prevents accidental loading of two bootstrappers
	 * on the same JVM, preps for display of the splash screen, etc.
	 * @param windowClass
	 */
	public ByuSwingBootstrapper(Class<T> windowClass) {
		super();

		EVENT_LOG = Logger.getLogger(String.format("%s", EVENT_LOG_NAME, windowClass.getSimpleName()));

		if (isInitialized) {
			throw new IllegalStateException("There is already an instance of this program running!");
		}
		isInitialized = true;
		final SplashScreen splashScreen = SplashScreen.getSplashScreen();

		LOG.debug("Setting up splash screen");

		setProgressBounds(getProgressBarBounds());

		setup(splashScreen, getSplashScreenURL(), getSplashScreenForegroundColor(), getMessageLocation());

		LOG.debug("Initialized Splash Screen");

		this.windowClass = windowClass;
	}

	private void startupSplashscreen() {
		new Thread(new Runnable() {

			public void run() {
				LOG.debug("setting visible = " + true);
				setVisible(true);
			}
		}).start();
	}

	/**
	 * Initialize spring related stuff
	 */
	private void initializeSpringContext() {
		LOG.info("Initializing Libraries");
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext();
		context.setConfigLocations(getContextLocations());
		try {
			context.refresh();
		} catch (Throwable t) {
			ExceptionInternalHandler.doHandle(t);
		}
		appContext = context;
		loginManager = getBean(CasAuthorizedClientLoginManager.class);
		SplashScreenForm.setPercent(100);
		LOG.info("Libraries Loaded");
	}

	/**
	 *
	 * @return Spring application context
	 */
	public static ApplicationContext getSpringApplicationContext() {
		return appContext;
	}

	/**
	 * Fetches a Spring bean
	 * @param <U> bean type
	 * @param beanId name of bean
	 * @return bean
	 */
	@SuppressWarnings("unchecked")
	public static <U> U getBean(final String beanId) {
		if (appContext == null) {
			return null;
		}
		Class type = appContext.getType(beanId);
		return (U) getBean(beanId, type);
	}

	/**
	 * Fetches a Spring bean
	 * @param <U> bean type
	 * @param klass bean class
	 * @return bean
	 */
	@SuppressWarnings("unchecked")
	public static <U> U getBean(final Class<U> klass) {
		LOG.debug("finding bean with class: " + klass);

		if (appContext == null) {
			throw new IllegalStateException("Application is not yet initialized");
		}

		String[] names = appContext.getBeanNamesForType(klass);

		if (names.length == 0) {
			throw new IllegalStateException("No beans found with interface: " + klass);
		}

		if (names.length > 1) {
			StringBuffer sb = new StringBuffer();
			Iterator<String> i = Arrays.asList(names).iterator();
			while (i.hasNext()) {
				String name = i.next();
				sb.append(name);
				if (i.hasNext()) {
					sb.append(", ");
				}
			}
			throw new IllegalStateException(names.length + " beans found with interface: " + klass + " [" + sb + "]");
		}

		//Get on EDT if it's a component
		String beanId = names[0];
		return (U) getBean(beanId, klass);
	}

	/**
	 * Fetches a Spring bean
	 * @param <U> bean type
	 * @param beanName bean name
	 * @param klass bean class
	 * @return bean
	 */
	@SuppressWarnings("unchecked")
	public static <U> U getBean(final String beanName, final Class<U> klass) {
		if (!Component.class.isAssignableFrom(klass) || EventQueue.isDispatchThread()) {
			return (U) appContext.getBean(beanName);
		}
		final BeanWrapper<U> wrapper = new BeanWrapper<U>();
		Runnable r = new Runnable() {

			public void run() {
				wrapper.setObj((U) appContext.getBean(beanName));
			}
		};
		if (SwingUtilities.isEventDispatchThread()) {
			r.run();
		} else {
			try {
				SwingUtilities.invokeAndWait(r);
			} catch (Exception ex) {
				throw new IllegalStateException("Error creating bean: " + beanName, ex);
			}
		}

		return wrapper.getObj();
	}

	// Used by all getBean implementations
	private static class BeanWrapper<U> {

		private U obj;

		public U getObj() {
			return obj;
		}

		public void setObj(U obj) {
			this.obj = obj;
		}
	}

	/**
	 * Gets net id of current user
	 * @return net id
	 */
	public static String getUserNetId() {
		if (loginManager == null) {
			return null;
		}
		return loginManager.getCurrentNetId();
	}

	/**
	 * Gets person id of current user
	 * @return person id
	 */
	public static String getUserPersonId() {
		if (loginManager == null) {
			return null;
		}
		return loginManager.getCurrentPersonId();
	}

	/**
	 * Starts application
	 */
	public void run() {
		LOG.info("Running BYU Swing Bootstrapper");

		try {
			startupSplashscreen();
		} catch (Throwable t) {
			ExceptionInternalHandler.doHandle(t);
		}

		try {
			setupBeforeSpring();
		} catch (Exception e) {
			ExceptionInternalHandler.doHandle(e);
			System.exit(-1);
		}

		try {
			initializeSpringContext();
		} catch (Exception e) {
			ExceptionInternalHandler.doHandle(e);
			System.exit(-1);
		}

		LOG.info("Registering Exception Handler");
		ExceptionHandlerImpl.doRegisterExceptionHandler();

		LOG.info("Starting program");
		EventQueue.invokeLater(new Runnable() {

			public void run() {
				LOG.info("Setting LAF");
				setLAF();

				T window = getBean(windowClass);
				Toolkit toolkit = window.getToolkit();
				toolkit.addAWTEventListener(new EventListener(), AWTEvent.MOUSE_EVENT_MASK | AWTEvent.ACTION_EVENT_MASK);

				addListeners(window);

				setWindow(window);

				LOG.info("Logging in");
				if (!login()) {
					System.exit(-1);
				}

				LOG.info("Loading Main Window");

				SplashScreenListener listener = getBean(SplashScreenListener.class);
				listener.setListen(false);
				setVisible(false);
				LOG.info("Showing Main Window");
				window.setVisible(true);
			}
		});
	}
	private final ButtonWatcher containerWatcher = new ButtonWatcher();

	private void addListeners(Component c) {
		if (c instanceof Container) {
			Container co = (Container) c;
			co.addContainerListener(containerWatcher);
			for (Component each : co.getComponents()) {
				addListeners(each);
			}
		}
		if (c instanceof AbstractButton) {
			((AbstractButton) c).addActionListener(containerWatcher);
		}
		if (c instanceof JTextField) {
			((JTextField) c).addActionListener(containerWatcher);
		}
	}

	private void removeListeners(Component c) {
		if (c instanceof Container) {
			Container co = (Container) c;
			co.removeContainerListener(containerWatcher);
			for (Component each : co.getComponents()) {
				removeListeners(each);
			}
		}
		if (c instanceof AbstractButton) {
			((AbstractButton) c).removeActionListener(containerWatcher);
		}
		if (c instanceof JTextField) {
			((JTextField) c).removeActionListener(containerWatcher);
		}
	}

	private class ButtonWatcher implements ContainerListener, ActionListener {

		@Override
		public void componentAdded(ContainerEvent e) {
			Component c = e.getChild();
			addListeners(c);

		}

		@Override
		public void componentRemoved(ContainerEvent e) {
			Component c = e.getChild();
			removeListeners(c);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			logActionEvent(e);
		}
	}

	private static volatile long lastEventTime = System.currentTimeMillis();

	public static long getLastEventTime() {
		return lastEventTime;
	}

	private class EventListener implements AWTEventListener {

		@Override
		public void eventDispatched(AWTEvent event) {
			lastEventTime = System.currentTimeMillis();
			if (event instanceof MouseEvent) {
				logMouseEvent((MouseEvent) event);
			} else if (event instanceof ActionEvent) {
				logActionEvent((ActionEvent) event);
			} else {
				EVENT_LOG.info(String.format("Event is not a mouse event or an action event: %s", event));
				return;
			}
		}
	}

	private void logActionEvent(ActionEvent e) {
		EVENT_LOG.info("ActionEvent");
		EVENT_LOG.info(String.format("ActionCommand: %s", e.getActionCommand()));
		if (e.getSource() instanceof Component) {
			StringBuilder sb = new StringBuilder("Path to source component ");
			appendComponentNamePath((Component) e.getSource(), sb);
			EVENT_LOG.info(sb);
		} else {
			EVENT_LOG.info(String.format("Source: %s", e.getSource()));
		}
	}

	private boolean logMouseEvent(MouseEvent e) {
		if (e.getID() != MouseEvent.MOUSE_CLICKED) {
			return true;
		}
		StringBuilder sb = new StringBuilder("Mouse Clicked");
		Component c = e.getComponent();
		appendComponentNamePath(c, sb);
		EVENT_LOG.info(sb);
		return false;
	}

	private void appendComponentNamePath(Component c, StringBuilder sb) {
		if (c instanceof JScrollPane) {
			JScrollPane p = (JScrollPane) c;
			if (p.getViewport() != null && p.getViewport().getView() != null) {
				sb.append(String.format(" -> %s", p.getViewport().getView().getName()));
			}
		}
		while (c != null) {
			if (c.getName() != null) {
				sb.append(String.format(" -> %s", c.getName()));
			}
			c = c.getParent();
		}
	}

	/**
	 * Runs login, allowing auto login from stored password
	 * @return {@code true} if login is successful
	 * @see login(boolean)
	 */
	public boolean login() {
		return login(true);
	}

	/**
	 * Runs login.  If {@code doAutoLogin} is true, will check for a saved
	 * login/password, and if found, will automatically log the user in.  If not,
	 * or if that login/password are invalid, will display a login dialog.
	 * @param doAutoLogin
	 * @return {@code true} if login is successful
	 */
	public boolean login(boolean doAutoLogin) {
		LOG.info("Logging in");
		if (loginManager.getCurrentNetId() != null) {
			if (logout()) {
				return login(doAutoLogin);
			}
			return false;
		}
		CasAuthorizedStatus status = CasAuthorizedStatus.CANCEL;
		//TODO: fix
		try {
			status = loginManager.login(doAutoLogin);
		} catch (Exception ex) {
			ExceptionInternalHandler.doHandle(ex);

		}
		if (status == CasAuthorizedStatus.CANCEL) {
			System.exit(-1);
		}
		LOG.info("status: " + status);
		boolean isAuthorized = isAuthorized();
		LOG.info("isAuthorized: " + isAuthorized);
		if (!isAuthorized) {
			if (loginManager.getCurrentPersonId() != null) {
				loginManager.logout();
			}
			int option = JOptionPane.showConfirmDialog(null,
					"You do not have sufficient privileges to run this application.\n"
					+ "Do you want to try logging again?\n"
					+ "If not, the application will exit.",
					"Application Error", JOptionPane.YES_NO_OPTION);
			if (option == JOptionPane.YES_OPTION) {
				return login(false);
			}
			return false;
		}
		return true;
	}

	/**
	 * Logs out the current user.  Displays confirmation dialog and, if user confirms,
	 * logs out the user.
	 * @return {@code true} if user was logged out
	 */
	public boolean logout() {
		if (loginManager.getCurrentNetId() == null) {
			return false;
		}
		int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to logout?", "Logout", JOptionPane.YES_NO_OPTION);
		if (option == JOptionPane.YES_OPTION) {
			loginManager.logout();
			return true;
		}
		return false;
	}

	/**
	 * Checks if the current user is part of the specifed GRO group
	 * @param groupId GRO group to check
	 * @return {@code true} if user is member of group
	 */
	protected static boolean hasGroup(String groupId) {
		if (loginManager.getGroups() == null) {
			return false;
		}
		LOG.debug("Checking groupId: " + groupId);
		return loginManager.getGroups().contains(groupId);
	}

	/**
	 * Enables/disables a component on the EDT
	 * <p><b>NOTE:</b> This method should only be used as a last resort.
	 * Bindings, BYUTask, and simply threading things properly should be tried
	 * before using this method.
	 * @param component component to disable
	 * @param enable
	 */
	public static void setEnabled(final Component component, final boolean enable) {
		if (SwingUtilities.isEventDispatchThread()) {
			component.setEnabled(enable);
			return;
		}
		Runnable r = new Runnable() {

			public void run() {
				component.setEnabled(enable);
			}
		};
		SwingUtilities.invokeLater(r);
	}

	/**
	 * Selects/deselects a button on the EDT
	 * <p><b>NOTE:</b> This method should only be used as a last resort.
	 * Bindings, BYUTask, and simply threading things properly should be tried
	 * before using this method.
	 * @param button
	 * @param select
	 */
	public static void setSelected(final AbstractButton button, final boolean select) {
		if (SwingUtilities.isEventDispatchThread()) {
			button.setSelected(select);
			return;
		}
		Runnable r = new Runnable() {

			public void run() {
				button.setSelected(select);
			}
		};
		SwingUtilities.invokeLater(r);
	}

	/**
	 * Sets a component's visibility on the EDT
	 * <p><b>NOTE:</b> This method should only be used as a last resort.
	 * Bindings, BYUTask, and simply threading things properly should be tried
	 * before using this method.
	 * @param component
	 * @param visible
	 */
	public static void setVisible(final Component component, final boolean visible) {
		if (SwingUtilities.isEventDispatchThread()) {
			component.setVisible(visible);
		}
		Runnable r = new Runnable() {

			public void run() {
				component.setVisible(visible);
			}
		};
		SwingUtilities.invokeLater(r);
	}
}
