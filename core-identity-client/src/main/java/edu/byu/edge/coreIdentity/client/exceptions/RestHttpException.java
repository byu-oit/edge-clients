package edu.byu.edge.coreIdentity.client.exceptions;

import java.net.MalformedURLException;

/**
 * Created by Scott Hutchings on 8/30/2016.
 */
public class RestHttpException extends Exception {
	public RestHttpException(String s) {
		super(s);
	}

	public RestHttpException(String s, Exception e) {
		super(s, e);
	}
}
