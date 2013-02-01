/*
 * Filename: ExceptionHandler
 * Created: Dec 15, 2008
 */
package edu.byu.framework.swing.exceptions;

/**
 *
 * @author tylers2
 */
public interface ExceptionHandler {

    public void addListener(ExceptionListener listener);

    public void registerExceptionHandler();

    public void handleException(Throwable t);
}
