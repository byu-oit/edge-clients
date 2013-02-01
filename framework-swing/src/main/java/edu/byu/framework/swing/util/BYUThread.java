package edu.byu.framework.swing.util;

import edu.byu.framework.swing.exceptions.ExceptionInternalHandler;
import edu.byu.framework.swing.exceptions.ExceptionListener;

import java.awt.EventQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;

/** @author jmooreoa */
public abstract class BYUThread {

	private static final AtomicInteger taskNum = new AtomicInteger(0);
	private static final ConcurrentLinkedQueue<TaskListener> listeners = new ConcurrentLinkedQueue<TaskListener>();
	private static final Logger STATIC_LOG = Logger.getLogger(BYUThread.class);

	protected final int taskNumber = taskNum.getAndIncrement();

	private final Logger LOG = Logger.getLogger("BYUTask.task" + taskNumber + ":" + getClass().getSimpleName());

	protected volatile boolean hadError = false;

	public static void addTaskListener(TaskListener listener) {
		STATIC_LOG.debug("Adding task listener: " + listener);
		listeners.add(listener);
	}

	public static boolean removeTaskListener(TaskListener listener) {
		STATIC_LOG.debug("Removing task listener: " + listener);
		return listeners.remove(listener);
	}

	//Forward methods here to allow for future changes in exception handling
	public static void addExceptionListener(ExceptionListener listener) {
		STATIC_LOG.debug("Adding exception listener: " + listener);
		ExceptionInternalHandler.addListener(listener);
	}

	public static boolean removeExceptionListener(ExceptionListener listener) {
		STATIC_LOG.debug("Removing exception listener: " + listener);
		return ExceptionInternalHandler.removeListener(listener);
	}

	public final boolean hadError() {
		return hadError;
	}

	protected int tries;

	protected Retry getRetry() {
		try {
			return getClass().getDeclaredMethod("doInBackground", (Class<?>[]) null).getAnnotation(Retry.class);
		} catch (NoSuchMethodException ex) {
			return null;
		} catch (SecurityException ex) {
			return null;
		}
	}

	protected boolean processThrowable(Throwable t) {
		Retry retry = getRetry();
		if (retry == null) {
			return false;
		}
		LOG.debug("Retrying after exception " + t.toString());
		int retries = retry.tries();
		if (retries > Retry.MAX_RETRIES) {
			retries = Retry.MAX_RETRIES;
		}
		if (retries < Retry.MIN_RETRIES) {
			retries = Retry.MIN_RETRIES;
		}
		if (tries > retries) {
			LOG.debug("Hit retry limit");
			return false;
		}
		LOG.debug(String.format("Try %d of %d", tries, retries));
		Class<? extends Throwable> throwableClass = t.getClass();
		for (Class<? extends Throwable> each : retry.die()) {
			if (throwableClass.equals(each)) {
				LOG.debug("Thowable is a non-retryable exception");
				return false;
			}
		}
		for (Class<? extends Throwable> each : retry.retry()) {
			if (each.isAssignableFrom(throwableClass)) {
				LOG.debug("Throwable is a retryable exception");
				return true;
			}
		}
		LOG.debug("Throwable does not match any retryable exceptions, dying");
		return false;
	}

	protected final void notifyStart() {
		LOG.debug("Preparing to notify listeners of start");
		final TaskEvent event = new TaskEvent(this, hadError);
		Runnable r = new Runnable() {
			public void run() {
				LOG.debug("Notifiying task listeners of start: " + event.toString());
				for (TaskListener each : listeners) {
					each.taskStarted(event);
				}
			}
		};
		if (EventQueue.isDispatchThread()) {
			r.run();
		} else {
			try {
				EventQueue.invokeAndWait(r);
			} catch (Exception ex) {
				throw new IllegalStateException("Error notifying listeners", ex);
			}
		}
	}

	protected final void notifyEnd(Throwable t) {
		TaskEvent event = new TaskEvent(this, hadError);
		event.setTries(tries);
		event.setError(t);
		notifyEnd(event);
	}

	protected final void notifyEnd(final TaskEvent event) {
		LOG.debug("Preparing to notify listeners of end");
		Runnable r = new Runnable() {
			public void run() {
				LOG.debug("Notifying task listeners of end: " + event.toString());
				for (TaskListener each : listeners) {
					each.taskFinished(event);
				}
			}
		};
		if (EventQueue.isDispatchThread()) {
			r.run();
		} else {
			try {
				EventQueue.invokeAndWait(r);
			} catch (Exception ex) {
				throw new IllegalStateException("Error notifying listeners", ex);
			}
		}
	}

	protected final void notifyEnd() {
		TaskEvent event = new TaskEvent(this, hadError);
		event.setTries(tries);
		notifyEnd(event);
	}

}
