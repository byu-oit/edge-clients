package edu.byu.edge.client.auth;

import edu.byu.edge.client.domain.Nonce;
import edu.byu.edge.client.domain.WsSession;

/**
 * Author: Wyatt Taylor (wyatt_taylor@byu.edu)
 * Date: 6/6/12
 */
public interface ByuAuthClient {

	/**
	 * Logs a user in. Uses the client default time-out of 15 minutes.
	 * @param netId the Route-Y NetID
	 * @param password the user's password
	 * @return the web service session (temporary API key)
	 */
	public WsSession login(String netId, String password);

	/**
	 * Logs a user in.
	 * @param netId the Route-Y NetID
	 * @param password the user's password
	 * @param timeout the session timeout in minutes, valid values are 1 to 480
	 * @return the web service session (temporary API key)
	 */
	public WsSession login(String netId, String password, int timeout);

	/**
	 * Renew web service session with the default timeout.
	 * @param session the session to renew
	 * @return the new web service session
	 */
	public WsSession renew(WsSession session);

	/**
	 * Renew web service session with the default timeout.
	 * @param session the session to renew
	 * @param timeout the session timeout in minutes, valid values are 1 to 480
	 * @return the new web service session
	 */
	public WsSession renew(WsSession session, int timeout);

	/**
	 * Terminate the web service session.
	 * @param session the sesssion to terminate
	 */
	public void logout(WsSession session);

	/**
	 * Obtain a nonce for a service call.
	 * @param session the user's web service session
	 * @return a single-use nonce
	 */
	public Nonce obtainNonce(WsSession session);

	/**
	 * Obtain a nonce for a service call with an actor.
	 * @param session the user's web service session
	 * @param actorNetId the netId of the person for whom the call is being made
	 * @return a single-use nonce
	 */
	public Nonce obtainNonce(WsSession session, String actorNetId);
}
