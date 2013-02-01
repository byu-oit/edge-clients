package edu.byu.swing.components;

import edu.byu.framework.swing.util.BYUTask;
import javax.swing.JPanel;
import org.apache.log4j.Logger;
import org.divxdede.swing.busy.BusyModel;

/**
 * A lockable panel.  When locked, this panel will not allow interaction with
 * its sub-components and will paint a busy signal.  By default, it displays
 * an indeterminate working symbol.  Use {@code getModel} to get the
 * {@code BusyModel} and change the display options.
 *
 * @version 1.1.0
 * @since 1.0.0
 * @author jmooreoa
 */
public class LockablePanel extends JPanel {

    private BusyModel model;
    private static final Logger LOG = Logger.getLogger(LockablePanel.class);

    /**
     * Gets the model associated with this panel
     * @return {@code BusyModel}
     */
    public BusyModel getBusyModel() {
        return model;
    }

    public void setBusyModel(BusyModel model) {
        this.model = model;
    }

    /**
     * Locks the panel
     */
    public void lockPanel() {
        if (model == null) {
            LOG.warn("Cannot lock panel", new IllegalStateException("Cannot lock/unlock panel without providing a BusyModel"));
            return;
            //            throw new IllegalStateException("Cannot lock/unlock panel without providing a BusyModel");
        }
        model.setBusy(true);
    }

    /**
     * Unlocks the panel
     */
    public void unlockPanel() {
        if (model == null) {
            LOG.warn("Cannot unlock panel", new IllegalStateException("Cannot lock/unlock panel without providing a BusyModel"));
            return;
            //throw new IllegalStateException("Cannot lock/unlock panel without providing a BusyModel");
        }
        model.setBusy(false);
    }

    /**
     *
     * @return {@code true} if panel is locked
     */
    public boolean isLocked() {
        if (model == null) {
            LOG.warn("Cannot determine locking status", new IllegalStateException("Cannot lock/unlock panel without providing a BusyModel"));
            return false;
            //throw new IllegalStateException("Cannot lock/unlock panel without providing a BusyModel");
        }
        return model.isBusy();
    }

    protected abstract class LockingTask extends BYUTask {

        @Override
        protected void setup() {
            lockPanel();
        }

        @Override
        protected void tearDown() {
            unlockPanel();
        }
    }
}
