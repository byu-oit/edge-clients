package edu.byu.auth.client;

import edu.byu.auth.domain.Nonce;

/**
 * Author: Wyatt Taylor (wyatt_taylor@byu.edu)
 * Date: 08/29/2014
 *
 * @author Wyatt Taylor (wyatt_taylor@byu.edu)
 * @since 08/29/2014
 */
public interface CredentialClient {

	/**
	 * This obtains a nonce.
	 *
	 * @return nonce
	 */
	public Nonce obtainNonce();

	/**
	 * This returns the value to put in the authorization header in a request. Calls obtain nonce internally.
	 *
	 * @return authorization header value
	 */
	public String obtainAuthorizationHeaderString();
}
