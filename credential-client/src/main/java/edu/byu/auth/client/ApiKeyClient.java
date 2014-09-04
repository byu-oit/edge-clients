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

	public static final String ACTOR_NET_ID = "netId";
	public static final String ACTOR_BYU_ID = "byuId";
	public static final String ACTOR_PERSON_ID = "personId";

	/**
	 * This obtains a nonce for an actor
	 *
	 * @param actorId The net-id of the actor.
	 * @return nonce
	 */
	public Nonce obtainNonceWithActor(final String actorId);

	/**
	 * This obtains a nonce for an actor
	 *
	 * @param actorId The id of the actor.
	 * @param idType The type of id of the actor.
	 * @return nonce
	 */
	public Nonce obtainNonceWithActor(final String actorId, final String idType);

}
