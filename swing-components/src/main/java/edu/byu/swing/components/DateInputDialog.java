package edu.byu.swing.components;

import edu.byu.framework.swing.exceptions.ExceptionInternalHandler;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import org.jdesktop.swingx.JXDatePicker;

/**
 *
 * @author jmooreoa
 */
public class DateInputDialog extends JDialog implements ActionListener {

    private JLabel lblMessage = new JLabel();
    private JXDatePicker dtp = new JXDatePicker();
    private Date date;
    private static final String OK = "OK", CANCEL = "Cancel";
    private static final String DEFAULT_MESSAGE = "Select a date";
    public static final String DEFAULT_TITLE = "Choose a date";

    public DateInputDialog(Window owner) {
        super(owner);
        initComponents();
    }

    public DateInputDialog(Dialog owner) {
        super(owner);
        initComponents();
    }

    public DateInputDialog(Frame owner) {
        super(owner);
        initComponents();
    }

    private void initComponents() {
        BorderLayout layout = new BorderLayout();
        layout.setVgap(5);
        setLayout(layout);
        add(lblMessage, BorderLayout.NORTH);
        add(dtp);

        Box buttonBox = new Box(BoxLayout.X_AXIS);
        buttonBox.add(Box.createHorizontalGlue());
        JButton btnOkay = new JButton(OK);
        btnOkay.addActionListener(this);
        JButton btnCancel = new JButton(CANCEL);
        btnCancel.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals(OK)) {
            date = dtp.getDate();
        }
        this.setVisible(false);
    }

    /**
     * Shows the date input dialog.
     * <b>Note:</b> this method is thread-safe and can be run on any thread
     * @param parent parent component (over which dialog will be centered)
     * @param message displayed prompt
     * @param title title
     * @return a date, or null if user cancelled or did not select a date
     */
    public Date showDateInputDialog(final Component parent, final String message, final String title) {
        Runnable r = new Runnable() {

            public void run() {
                setLocationRelativeTo(parent);
                lblMessage.setText(message);
                setTitle(title);
                dtp.setDate(null);
                date = null;
                pack();
                DateInputDialog.this.setVisible(true);
                date = dtp.getDate();
            }
        };
        if (SwingUtilities.isEventDispatchThread()) {
            r.run();
        } else {
            try {
                SwingUtilities.invokeAndWait(r);
            } catch (Exception ex) {
                ExceptionInternalHandler.doHandle(ex);
                return null;
            }
        }
        return date;
    }

    /**
     * Shows the date input dialog.
     * <b>Note:</b> this method is thread-safe and can be run on any thread
     * @param message displayed prompt
     * @param title title
     * @return a date, or null if user cancelled or did not select a date
     */
    public Date showDateInputDialog(String message, String title) {
        return showDateInputDialog(super.getParent(), message, title);
    }

    /**
     * Shows the date input dialog.
     * <b>Note:</b> this method is thread-safe and can be run on any thread
     * @param parent parent component (over which dialog will be centered)
     * @return a date, or null if user cancelled or did not select a date
     */
    public Date showDateInputDialog(Component parent) {
        return showDateInputDialog(parent, DEFAULT_MESSAGE, DEFAULT_TITLE);
    }

    /**
     * Shows the date input dialog.
     * <b>Note:</b> this method is thread-safe and can be run on any thread
     * @return a date, or null if user cancelled or did not select a date
     */
    public Date showDateInputDialog() {
        return showDateInputDialog(super.getParent(), DEFAULT_MESSAGE, DEFAULT_TITLE);
    }
}
