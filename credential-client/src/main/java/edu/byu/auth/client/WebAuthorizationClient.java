package edu.byu.auth.client;

/**
 * Created by Scott Hutchings on 8/29/2016.
 */
public interface WebAuthorizationClient {

	/**
	 * This returns the value to put in the authorization header in a request. Calls obtain nonce internally.
	 *
	 * @return authorization header value
	 */
	public String obtainAuthorizationHeaderString();
}
