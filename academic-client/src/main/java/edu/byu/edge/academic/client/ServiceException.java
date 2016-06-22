package edu.byu.edge.academic.client;

/**
 * Created by wct5 on 2/10/16.
 */
public class ServiceException extends Exception {

	public ServiceException() {
	}

	public ServiceException(final String s) {
		super(s);
	}

	public ServiceException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public ServiceException(final Throwable cause) {
		super(cause);
	}

	@Override
	public String toString() {
		return "ServiceException - " + super.toString();
	}
}
