package edu.byu.framework.swing.util;

import java.awt.Component;
import java.awt.EventQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractButton;
import javax.swing.JComboBox;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

/**
 *
 * @author jmooreoa
 */
@Deprecated
public final class ThreadUtilities {

    /**
     * Runs a background task
     * @param r <code>Runnable</code> to execute
     * @see runTask(Runnable, boolean)
     * @see java.lang.Runnable
     * @deprecated
     */
    @Deprecated
    public static void runTask(Runnable r) {
        runTask(r, false);
    }

    /**
     * If runOnEDT is true will execute the thread on the Event Dispatch Thread
     * Otherwise it will use a SwingWorker
     * @param r <code>Runnable</code> to execute
     * @param runOnEDT run on Event Dispatching Thread
     *
     * @see javax.swing.SwingWorker
     * @see java.lang.Runnable
     * @deprecated
     */
    @Deprecated
    public static void runTask(final Runnable r, boolean runOnEDT) {
        if (runOnEDT) {
            SwingUtilities.invokeLater(r);
            return;
        }

        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {

            @Override
            protected Void doInBackground() throws Exception {
                r.run();
                return null;
            }
        };
        worker.execute();
    }

    private static class ResultWrapper<T> {
        private T obj;

        public T getObj() {
            return obj;
        }

        public void setObj(T obj) {
            this.obj = obj;
        }

    }

    @Deprecated
    public static <U> U runComputation(final Callable<U> c, boolean runOnEDT) {
        final ResultWrapper<U> wrapper = new ResultWrapper<U>();
        final CountDownLatch go = new CountDownLatch(1);
        if (EventQueue.isDispatchThread() && runOnEDT) {
            try {
                return c.call();
            } catch (Exception ex) {
                throw new RuntimeException("Could not complete computation", ex);
            }
        }
        Runnable r = new Runnable() {
            public void run() {
                try {
                    wrapper.setObj(c.call());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                go.countDown();
            }
        };
        runTask(r, runOnEDT);
        try {
            go.await();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        return wrapper.getObj();
    }

    /**
     * Thread-friendly wrapper method for <code>Component.setEnabled</code>.
     * @param enable
     * @param components
     * @deprecated
     */
    @Deprecated
    public static void setEnabled(final boolean enable, final Component... components) {
        Runnable r = new Runnable() {

            public void run() {
                for (Component comp : components) {
                    comp.setEnabled(enable);
                }
            }
        };

        if (EventQueue.isDispatchThread()) {
            r.run();
            return;
        }

        runTask(r, true);
    }

    /**
     * Thread-friendly wrapper method for <code>Component.setVisible()</code>.
     * @param visible
     * @param components
     *
     * @see java.awt.Component.setVisible(boolean)
     */
    @Deprecated
    public static void setVisible(final boolean visible, final Component... components) {
        Runnable r = new Runnable() {

            public void run() {
                for (Component c : components) {
                    c.setVisible(visible);
                }
            }
        };
        if (EventQueue.isDispatchThread()) {
            r.run();
            return;
        }

        runTask(r, true);
    }

    /**
     * Thread-friendly wrapper method for <code>AbstractButton.setSelected</code>.
     * @param selected
     * @param buttons AbstractButtons to select/deselect
     *
     * @see javax.swing.AbstractButton.setSelected(boolean)
     * @deprecated
     */
    @Deprecated
    public static void setSelected(final boolean selected, final AbstractButton... buttons) {
        Runnable r = new Runnable() {

            public void run() {
                for (AbstractButton button : buttons) {
                    button.setSelected(selected);
                }
            }
        };

        if (EventQueue.isDispatchThread()) {
            r.run();
            return;
        }

        runTask(r, true);
    }

    /**
     * Thread-friendly wrapper method for <code>JComboBox.setSelectedItem</code>.
     * @param component
     * @param selectedItem
     *
     * @see javax.swing.JComboBox.setSelectedItem(Object)
     * @deprecated
     */
    @Deprecated
    public static void setSelectedItem(final JComboBox component, final Object selectedItem) {
        Runnable r = new Runnable() {

            public void run() {
                component.setSelectedItem(selectedItem);
            }
        };

        if (EventQueue.isDispatchThread()) {
            r.run();
            return;
        }

        runTask(r, true);
    }
}
