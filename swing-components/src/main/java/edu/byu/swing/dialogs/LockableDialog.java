package edu.byu.swing.dialogs;

import edu.byu.framework.swing.util.BYUTask;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Dialog.ModalityType;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.Window;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import org.divxdede.swing.busy.BusyModel;
import org.divxdede.swing.busy.JBusyComponent;

/**
 *
 * @author jmooreoa
 */
public class LockableDialog extends JDialog {

    private BusyModel model;

    public LockableDialog(Window owner, String title, ModalityType modalityType, GraphicsConfiguration gc) {
        super(owner, title, modalityType, gc);
        initBusyComponent();
    }

    public LockableDialog(Window owner, String title, ModalityType modalityType) {
        super(owner, title, modalityType);
        initBusyComponent();
    }

    public LockableDialog(Window owner, String title) {
        super(owner, title);
        initBusyComponent();
    }

    public LockableDialog(Window owner, ModalityType modalityType) {
        super(owner, modalityType);
        initBusyComponent();
    }

    public LockableDialog(Window owner) {
        super(owner);
        initBusyComponent();
    }

    public LockableDialog(Dialog owner, String title, boolean modal, GraphicsConfiguration gc) {
        super(owner, title, modal, gc);
        initBusyComponent();
    }

    public LockableDialog(Dialog owner, String title, boolean modal) {
        super(owner, title, modal);
        initBusyComponent();
    }

    public LockableDialog(Dialog owner, String title) {
        super(owner, title);
        initBusyComponent();
    }

    public LockableDialog(Dialog owner, boolean modal) {
        super(owner, modal);
        initBusyComponent();
    }

    public LockableDialog(Dialog owner) {
        super(owner);
        initBusyComponent();
    }

    public LockableDialog(Frame owner, String title, boolean modal, GraphicsConfiguration gc) {
        super(owner, title, modal, gc);
        initBusyComponent();
    }

    public LockableDialog(Frame owner, String title, boolean modal) {
        super(owner, title, modal);
        initBusyComponent();
    }

    public LockableDialog(Frame owner, String title) {
        super(owner, title);
        initBusyComponent();
    }

    public LockableDialog(Frame owner, boolean modal) {
        super(owner, modal);
        initBusyComponent();
    }

    public LockableDialog(Frame owner) {
        super(owner);
        initBusyComponent();
    }

    public LockableDialog() {
        initBusyComponent();
    }
    private JComponent content = new JPanel(new BorderLayout());
    private JBusyComponent wrapper;

    private void initBusyComponent() {
        wrapper = new JBusyComponent(content);
        super.setContentPane(wrapper);
        this.model = wrapper.getBusyModel();
    }

    @Override
    public Container getContentPane() {
        return content;
    }

    @Override
    public void setContentPane(Container contentPane) {
        if (contentPane == null) {
            throw new IllegalArgumentException("contentPane cannot be null");
        }
        if (contentPane instanceof JBusyComponent) {
            super.setContentPane(contentPane);
        }
        if (!(contentPane instanceof JComponent)) {
            throw new IllegalArgumentException("contentPane must be a JComponent");
        }
        this.content = (JComponent) contentPane;
        wrapper.setView(content);
    }

    protected void lock() {
        model.setBusy(true);
    }

    protected void unlock() {
        model.setBusy(false);
    }

    protected boolean isLocked() {
        return model.isBusy();
    }

    @Override
    protected void firePropertyChange(final String propertyName, final Object oldValue, final Object newValue) {
        doPropertyChange(new Runnable() {

            public void run() {
                LockableDialog.super.firePropertyChange(propertyName, oldValue, newValue);
            }
        });
    }

    @Override
    protected void firePropertyChange(final String propertyName, final boolean oldValue, final boolean newValue) {
        doPropertyChange(new Runnable() {

            public void run() {
                LockableDialog.super.firePropertyChange(propertyName, oldValue, newValue);
            }
        });
    }

    @Override
    protected void firePropertyChange(final String propertyName, final int oldValue, final int newValue) {
        doPropertyChange(new Runnable() {

            public void run() {
                LockableDialog.super.firePropertyChange(propertyName, oldValue, newValue);
            }
        });
    }

    @Override
    public void firePropertyChange(final String propertyName, final byte oldValue, final byte newValue) {
        doPropertyChange(new Runnable() {

            public void run() {
                LockableDialog.super.firePropertyChange(propertyName, oldValue, newValue);
            }
        });
    }

    @Override
    public void firePropertyChange(final String propertyName, final char oldValue, final char newValue) {
        doPropertyChange(new Runnable() {

            public void run() {
                LockableDialog.super.firePropertyChange(propertyName, oldValue, newValue);
            }
        });
    }

    @Override
    public void firePropertyChange(final String propertyName, final short oldValue, final short newValue) {
        doPropertyChange(new Runnable() {

            public void run() {
                LockableDialog.super.firePropertyChange(propertyName, oldValue, newValue);
            }
        });
    }

    @Override
    public void firePropertyChange(final String propertyName, final long oldValue, final long newValue) {
        doPropertyChange(new Runnable() {

            public void run() {
                LockableDialog.super.firePropertyChange(propertyName, oldValue, newValue);
            }
        });
    }

    @Override
    public void firePropertyChange(final String propertyName, final float oldValue, final float newValue) {
        doPropertyChange(new Runnable() {

            public void run() {
                LockableDialog.super.firePropertyChange(propertyName, oldValue, newValue);
            }
        });
    }

    @Override
    public void firePropertyChange(final String propertyName, final double oldValue, final double newValue) {
        doPropertyChange(new Runnable() {

            public void run() {
                LockableDialog.super.firePropertyChange(propertyName, oldValue, newValue);
            }
        });
    }

    private void doPropertyChange(Runnable r) {
        SwingUtilities.invokeLater(r);
    }

    protected abstract class LockingTask extends BYUTask {

        @Override
        protected void setup() {
            lock();
        }

        @Override
        protected void tearDown() {
            unlock();
        }
    }
}
