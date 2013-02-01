package edu.byu.framework.swing.util;

import edu.byu.framework.swing.exceptions.ExceptionInternalHandler;
import java.awt.EventQueue;
import javax.swing.SwingUtilities;
import org.apache.log4j.Logger;

/**
 *
 * @author tylers2
 */
public abstract class BYUTask extends BYUThread implements Runnable {

    private final Logger LOG = Logger.getLogger("BYUTask.task" + taskNumber + ":" + getClass().getSimpleName());

    /**
     * This code is run on the EDT
     */
    protected void setup() {
    }

    /**
     * This is NOT run on the EDT - do not modify GUI state from here
     */
    protected abstract void doInBackground() throws Exception;

    /**
     * This code is run on the EDT
     */
    protected void tearDown() {
    }

    private boolean hasRun;

    private void doRun() {
        tries++;
        try {
            doInBackground();
        } catch (Throwable t) {
            boolean tryAgain = processThrowable(t);
            if (tryAgain) {
                doRun();
            } else {
                throw new IllegalStateException("Could not execute task", t);
            }
        }
    }

    private TaskOptions getTaskOptions() {
        try {
            return getClass().getDeclaredMethod("doInBackground", (Class[]) null).getAnnotation(TaskOptions.class);
        } catch (NoSuchMethodException ex) {
            return null;
        } catch (SecurityException ex) {
            return null;
        }
    }

    /**
     * Runs this task
     * @throws IllegalThreadStateException if task has already been run
     */
    public final void run() {
        if (hasRun) {
            throw new IllegalThreadStateException("Task instance has already been run");
        }
        LOG.debug("Setting up task threads");
        final Runnable start = new Runnable() {

            public void run() {
                LOG.debug("Running task setup");
                setup();
                LOG.debug("Finished task setup");
            }
        };

        final Runnable done = new Runnable() {

            @Override
            public void run() {
                LOG.debug("Running task tearDown");
                tearDown();
                LOG.debug("Finished tearDown");
                notifyEnd();
                hasRun = true;
            }
        };

        final Thread background = new Thread() {

            @Override
            public void run() {
                notifyStart();
                LOG.debug("Running background task");
                doRun();
                LOG.debug("Finished background task");
                try {
                    EventQueue.invokeAndWait(done);
                } catch (Throwable t) {
                    hadError = true;
                    ExceptionInternalHandler.doHandle(t);
                    notifyEnd(t);
                }
            }
        };
        background.setName("BYUTask-" + taskNumber + ":" + getClass().getSimpleName());

        background.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {

            public void uncaughtException(Thread t, Throwable e) {
                System.out.println("Processing uncaught exception");
                hadError = true;
                if (EventQueue.isDispatchThread()) {
                    done.run();
                } else {
                    try {
                        EventQueue.invokeAndWait(done);
                    } catch (Throwable ex) {
                        ExceptionInternalHandler.doHandle(ex);
                    }
                }
                ExceptionInternalHandler.doHandle(e);
                notifyEnd(e);
            }
        });

        boolean proceed = true;
        LOG.debug("Invoking task threads");
        try {
            try {
                if (EventQueue.isDispatchThread()) {
                    start.run();
                } else {
                    EventQueue.invokeAndWait(start);
                }
            } catch (Throwable e) {
                try {
                    if (EventQueue.isDispatchThread()) {
                        done.run();
                    } else {
                        EventQueue.invokeAndWait(done);
                    }
                } catch (Throwable t) {
                    throw new IllegalStateException("Unable to start/cleanup task", t);
                }
                throw new IllegalStateException("Unable to start task", e);
            }
        } catch (Throwable t) {
            proceed = false;
            hadError = true;
            ExceptionInternalHandler.doHandle(t);
            notifyEnd(t);
        }
        if (!proceed) {
            return;
        }
        boolean newThread = false;
        TaskOptions to = getTaskOptions();
        if (to!= null) {
            newThread = to.newThread();
        }
        boolean spawn = SwingUtilities.isEventDispatchThread() || newThread;
        if (spawn) {
            background.start();
        } else {
            try {
                background.run();
            } catch (Throwable t) {
                background.getUncaughtExceptionHandler().uncaughtException(background, t);
            }
        }
    }

    @Deprecated
    public final void execute() {
        this.run();
    }
}