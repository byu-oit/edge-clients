package edu.byu.auth.client;

/**
 * Author: Wyatt Taylor (wyatt_taylor@byu.edu)
 * Date: 08/29/2014
 *
 * @author Wyatt Taylor (wyatt_taylor@byu.edu)
 * @since 08/29/2014
 */
public interface WsSessionClient extends CredentialClient {

	/**
	 * Logs in.
	 */
	public void login();

	/**
	 * Renews the current session.
	 */
	public void renew();

	/**
	 * Closes the session.
	 */
	public void logout();
}
