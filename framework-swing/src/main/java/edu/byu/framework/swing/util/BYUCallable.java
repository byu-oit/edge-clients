package edu.byu.framework.swing.util;

import java.util.concurrent.Callable;

/**
 *
 * @author jmooreoa
 */
public abstract class BYUCallable<T> extends BYUThread implements Callable<T> {

    protected abstract T doInBackground() throws Exception;

    private T doCall() throws Exception {
        tries++;
        try {
            return doInBackground();
        } catch (Exception e) {
            boolean tryAgain = processThrowable(e);
            if (tryAgain) {
                return doCall();
            } else {
                hadError = true;
                notifyEnd(e);
                throw e;
            }
        }
    }

    public final T call() throws Exception {
        if (hasRun) {
            throw new IllegalThreadStateException("This callable has already been run!");
        }
        hasRun = true;
        notifyStart();
        T result = doCall();
        notifyEnd();
        return result;
    }

    private boolean hasRun;
}
