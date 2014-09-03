package edu.byu.auth.client;

import edu.byu.auth.domain.Nonce;

/**
 * Author: Wyatt Taylor (wyatt_taylor@byu.edu)
 * Date: 08/29/2014
 *
 * @author Wyatt Taylor (wyatt_taylor@byu.edu)
 * @since 08/29/2014
 */
public interface ApiKeyClient extends CredentialClient {

	/**
	 * This obtains a nonce for an actor
	 *
	 * @param actorId The id (usually net-id) of the actor.
	 * @return nonce
	 */
	public Nonce obtainNonceWithActor(final String actorId);

}
