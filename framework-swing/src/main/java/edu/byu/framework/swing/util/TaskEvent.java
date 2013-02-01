package edu.byu.framework.swing.util;

import java.util.EventObject;

/**
 *
 * @author jmooreoa
 * @since 1.0.0
 * @version 1.1.0
 */
public class TaskEvent extends EventObject{
    private final boolean hadError;
    private final BYUThread thread;
    private Throwable error;
    private int tries;
    private final long time;

    public TaskEvent(BYUThread source, boolean hadError) {
        super(source);
        this.thread = source;
        this.hadError = hadError;
        time = System.currentTimeMillis();
    }

    public Throwable getError() {
        return error;
    }

    public void setError(Throwable error) {
        this.error = error;
    }

    @Deprecated
    public boolean isHadError() {
        return hadError();
    }

    public boolean hadError() {
        return hadError;
    }

    @Deprecated
    public BYUTask getTask() {
        if (thread instanceof BYUTask)
            return (BYUTask) thread;
        else
            return null;
    }

    public BYUThread getThread() {
        return thread;
    }

    public long getTime() {
        return time;
    }

    public int getTries() {
        return tries;
    }

    public void setTries(int tries) {
        this.tries = tries;
    }

    @Override
    public String toString() {
        return "TaskEvent@" + time + " for BYUThread: " + thread + " hadError: " + hadError + ((hadError)? "error: " + error : "");
    }


}
