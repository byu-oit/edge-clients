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

	public static final String ACTOR_NET_ID = "netId";
	public static final String ACTOR_BYU_ID = "byuId";
	public static final String ACTOR_PERSON_ID = "personId";

	/**
	 * This obtains a nonce.
	 *
	 * @return nonce
	 */
	public Nonce obtainNonce();

	/**
	 * This obtains a nonce.
	 *
	 * @param actor the actor id
	 *
	 * @return nonce
	 */
	public Nonce obtainNonce(String actor);

	/**
	 * This obtains a nonce for an actor
	 *
	 * @param actorId The id of the actor.
	 * @param idType The type of id of the actor.
	 *
	 * @return nonce
	 */
	public Nonce obtainNonce(String actorId, String idType);

	/**
	 * This returns the value to put in the authorization header in a request. Calls obtain nonce internally.
	 *
	 * @return authorization header value
	 */
	public String obtainAuthorizationHeaderString();

	/**
	 * This returns the value to put in the authorization header in a request. Calls obtain nonce internally.
	 *
	 * @param actor the actor id
	 *
	 * @return authorization header value
	 */
	public String obtainAuthorizationHeaderString(String actor);

	/**
	 * This returns the value to put in the authorization header in a request. Calls obtain nonce internally.
	 *
	 * @param actor the actor id
	 * @param idType The type of id of the actor.
	 *
	 * @return authorization header value
	 */
	public String obtainAuthorizationHeaderString(String actor, String idType);
}
