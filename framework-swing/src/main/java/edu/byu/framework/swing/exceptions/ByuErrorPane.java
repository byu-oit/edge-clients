package edu.byu.framework.swing.exceptions;

import edu.byu.framework.swing.ByuSwingBootstrapper;
import edu.byu.framework.swing.util.DialogUtil;
import java.awt.Dimension;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;
import java.util.TreeSet;
import javax.swing.*;
import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author jmooreoa
 */
public class ByuErrorPane extends JDialog implements ActionListener {

    private static final String DETAILS_COMMAND = "details";
    private static final String COPY_COMMAND = "copy";
    private static final String CLOSE_COMMAND = "close";
    private static final String DETAILS_HIDDEN_TEXT = "Show Details";
    private static final String DETAILS_SHOWING_TEXT = "Hide Details";
    private static final Dimension DETAILS_HIDDEN_DIM = new Dimension(400, 150);
    private static final Dimension DETAILS_SHOWING_DIM = new Dimension(600, 500);
    private DetailPanel pnlDetails;
    private JButton btnDetails;
    private String message;

    public static void show(Throwable t) {
        show(null, t);
    }

    public static void show(final String message, final Throwable t) {
        Runnable r = new Runnable() {
            public void run() {
                new ByuErrorPane(message, t).setVisible(true);
            }
        };
        SwingUtilities.invokeLater(r);
    }

    private ByuErrorPane(String message, Throwable t) {
        super(DialogUtil.getSelectedWindow(), "Error");
        this.setName("ByuErrorPane");
        this.message = message;
        this.setModal(true);
        this.setLayout(new MigLayout(new LC().wrapAfter(1).fill().hideMode(2)));
        this.add(new JLabel("<html><body><h2>An Error Has Occurred", (Icon) UIManager.get("OptionPane.errorIcon"), JLabel.LEFT), new CC().dockNorth());
		this.add(new JLabel(message), new CC().dockNorth());

		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel buttons = new JPanel();
        buttons.setLayout(new BoxLayout(buttons, BoxLayout.X_AXIS));
        buttons.add(Box.createHorizontalGlue());
        btnDetails = new JButton(DETAILS_HIDDEN_TEXT);
        btnDetails.addActionListener(this);
        btnDetails.setActionCommand(DETAILS_COMMAND);
        btnDetails.setName("DetailsButton");

        JButton btnCopy = new JButton("Copy To Clipboard");
        btnCopy.setActionCommand(COPY_COMMAND);
        btnCopy.addActionListener(this);
        btnCopy.setName("CopyButton");

        JButton btnClose = new JButton("Close");
        btnClose.setActionCommand(CLOSE_COMMAND);
        btnClose.addActionListener(this);
        btnClose.setName("CloseButton");

        buttons.add(btnDetails);
        buttons.add(Box.createHorizontalStrut(5));
        buttons.add(btnCopy);
        buttons.add(Box.createHorizontalStrut(5));
        buttons.add(btnClose);

        this.add(buttons, new CC().dockSouth());

        pnlDetails = new DetailPanel(message, t);
        pnlDetails.setVisible(false);
        this.add(pnlDetails, new CC().grow());
        this.setSize(DETAILS_HIDDEN_DIM);
        DialogUtil.centerDialog(DialogUtil.getSelectedWindow(), this);
    }

    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        if (cmd == null) {
            return;
        }
        if (cmd.equals(DETAILS_COMMAND)) {
            details();
        } else if (cmd.equals(CLOSE_COMMAND)) {
            close();
        } else if (cmd.equals(COPY_COMMAND)) {
            copyDetails();
        }
    }

    private void copyDetails() {
        Clipboard clipboard = getToolkit().getSystemClipboard();
        StringSelection selection = new StringSelection(pnlDetails.getPlainError());
        clipboard.setContents(selection, selection);
    }
    private boolean detailsShowing;

    private void details() {
        detailsShowing = !detailsShowing;
        pnlDetails.setVisible(detailsShowing);
        if (detailsShowing) {
            btnDetails.setText(DETAILS_SHOWING_TEXT);
            this.setSize(DETAILS_SHOWING_DIM);
        } else {
            btnDetails.setText(DETAILS_HIDDEN_TEXT);
            this.setSize(DETAILS_HIDDEN_DIM);
        }
    }

    private void close() {
        this.dispose();
    }

    private static class DetailPanel extends JPanel {

        private final Throwable error;
        private JTextPane pane;
        private JScrollPane scrollPane;
        private String plainError;
        private String formattedError;
        private String message;

        public DetailPanel(String message, Throwable error) {
            this.setName("DetailPanel");
            this.message = message;
            this.error = error;
            initComponents();
            processThrowable();
        }

		@Override
		public void setVisible(boolean aFlag) {
			super.setVisible(aFlag);
            JScrollBar horiz = scrollPane.getHorizontalScrollBar();
            JScrollBar vert = scrollPane.getVerticalScrollBar();
			horiz.setValue(horiz.getMinimum());
            vert.setValue(vert.getMinimum());
		}

		public String getPlainError() {
            return plainError;
        }

        private void initComponents() {
            this.setLayout(new MigLayout(new LC().fill()));
            pane = new JTextPane();
            pane.setContentType("text/html");
            pane.setEditable(false);
            scrollPane = new JScrollPane(pane);
            this.add(scrollPane, new CC().grow().growY(1f));
        }

        private void processThrowable() {
            StringBuilder formatted = new StringBuilder("<html><body>");
            StringBuilder plain = new StringBuilder();
            String msg = (message != null && !message.isEmpty()) ? message : error.getMessage();
            formatted.append(String.format("<h4>Message:</h4><code>%s</code><br />", msg));
            plain.append(String.format("MESSAGE: %s\n", msg));
            formatted.append("<h4>Stack Trace:</h4>");
            plain.append("\nSTACK TRACE:\n\n");
            addThrowableToStack(error, formatted, plain, false);
            formatted.append("<h4>System Information:</h4>");
            plain.append("\nSYSTEM INFORMATION:\n\n");
            addSystemInformation(formatted, plain);
            formatted.append("<h4>Application Information:</h4>");
            plain.append("\nAPPLICATION INFORMATION:\n\n");
            addDebugInformation(formatted, plain);
            formatted.append("</body></html>");
            plainError = plain.toString();
            formattedError = formatted.toString();
            pane.setText(formattedError);
        }

        private void addThrowableToStack(Throwable t, StringBuilder formatted, StringBuilder plain, boolean isCause) {
            String caused = (isCause) ? "Caused By:" : "";
            String msg = String.format("%s %s", caused, t.getMessage());
            formatted.append("<p>");
            formatted.append(msg);
            plain.append(msg);
            formatted.append("</p>");
            formatted.append("<code>");
            plain.append("\n\n");
            for (StackTraceElement each : t.getStackTrace()) {
                String line = String.format("\t%s\n", each.toString());
                formatted.append(line);
                plain.append(line);
            }
            formatted.append("</code>");
            plain.append("\n\n");
            Throwable cause = t.getCause();
            if (cause != null) {
                addThrowableToStack(cause, formatted, plain, true);
            }
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

        private void addSystemInformation(StringBuilder formatted, StringBuilder plain) {
            StringBuilder details = new StringBuilder();

			for (String each : properties) {
				details.append(String.format("%s = %s\n", each, System.getProperty(each)));
			}

            details.append("\n\nUser Details:\n");
            details.append(String.format("User netid: %s", ByuSwingBootstrapper.getUserNetId()));

            formatted.append("<pre>");
            formatted.append(details);
            formatted.append("</pre>");

            plain.append(details);
        }

        private void addDebugInformation(StringBuilder formatted, StringBuilder plain) {
            StringBuilder debug = new StringBuilder();
            for (DebugAppender each : ExceptionInternalHandler.getDebugAppenders()) {
                CharSequence data = each.getDebugData(error);
                if (data == null) {
                    continue;
                }
                debug.append("\n--------\n");
                debug.append(data);
            }
            formatted.append("<pre>");
            formatted.append(debug);
            formatted.append("</pre>");
            plain.append(debug);
        }
    }

    public static void main(String[] args) throws InterruptedException {

        Throwable t1 = new IllegalStateException("1");
        Throwable t2 = new IllegalArgumentException("2", t1);
        final Throwable t3 = new IllegalStateException("3", t2);
        ExceptionInternalHandler.addAppender(new DebugAppender() {

            @Override
            public CharSequence getDebugData(Throwable t) {
                return "This is a test";
            }
        });
//         show("hello", t3);
        Runnable r = new ShowFrame(t3);
        SwingUtilities.invokeLater(r);
    }

    private static class ShowFrame implements Runnable {
        private final Throwable t;

        public ShowFrame(Throwable t) {
            this.t = t;
        }

        @Override
        public void run() {
            JFrame f = new JFrame("Test");
                JButton b = new JButton("Test");
                f.add(b);
                b.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        ExceptionInternalHandler.doHandle(t);
                    }
                });
                f.setVisible(true);
                f.setSize(1000, 800);
                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }

    }
}
