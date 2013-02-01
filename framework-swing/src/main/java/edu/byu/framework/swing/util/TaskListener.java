package edu.byu.framework.swing.util;

/**
 *
 * @author tylers2
 */
public interface TaskListener {

    public void taskStarted(TaskEvent event);

    public void taskFinished(TaskEvent event);
}
