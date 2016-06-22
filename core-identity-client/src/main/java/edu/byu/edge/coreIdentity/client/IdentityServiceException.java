package edu.byu.edge.coreIdentity.client;

/**
 * Created by wct5 on 2/10/16.
 */
public class IdentityServiceException extends Exception {

	public IdentityServiceException() {
	}

	public IdentityServiceException(final String s) {
		super(s);
	}

	public IdentityServiceException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public IdentityServiceException(final Throwable cause) {
		super(cause);
	}

	@Override
	public String toString() {
		return "ServiceException - " + super.toString();
	}
}
