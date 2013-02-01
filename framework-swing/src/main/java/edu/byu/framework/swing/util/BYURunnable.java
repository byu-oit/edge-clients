package edu.byu.framework.swing.util;

/**
 *
 * @author jmooreoa
 */
public abstract class BYURunnable extends BYUThread implements Runnable {

    protected abstract void doInBackground() throws Exception;

    private boolean hasRun;

    public final void run() {

        if (hasRun) {
            throw new IllegalThreadStateException("This runnable has already been run!");
        }
        hasRun = true;
        doRun();
    }

    private void doRun() {
        tries++;
        try {
            doInBackground();
        } catch (Exception e) {
            boolean tryAgain = processThrowable(e);
            if (tryAgain) {
                hadError = true;
                doRun();
            } else {
                notifyEnd(e);
                throw new IllegalStateException("Could not execute task", e);
            }
        }
    }
}
