/*
 * Filename: ExceptionHandlerImpl
 * Created: Dec 15, 2008
 */
package edu.byu.framework.swing.exceptions;

import org.springframework.stereotype.Repository;
import org.apache.log4j.Logger;

/**
 *
 * @author tylers2
 */
@Repository
public class ExceptionHandlerImpl implements ExceptionHandler {

    private final static Logger LOG = Logger.getLogger(ExceptionHandlerImpl.class);

	public void addListener(ExceptionListener listener) {
		ExceptionInternalHandler.addListener(listener);
	}

	public void registerExceptionHandler() {
        doRegisterExceptionHandler();
    }

    public static void doRegisterExceptionHandler() {
        LOG.info("Registering exception handler");
		System.setProperty("sun.awt.exception.handler", ExceptionInternalHandler.class.getName());
	}

    public void handleException(Throwable t) {
        ExceptionInternalHandler.doHandle(t);
    }

}
