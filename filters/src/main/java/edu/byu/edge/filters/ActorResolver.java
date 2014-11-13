package edu.byu.edge.filters;

/**
 * Created by wct5 on 11/13/14.
 */
public interface ActorResolver {
	/**
	 * Implementors must resolve the actor. If the actor is not a NET-ID, it must be in the format of actorId/idType (such as 011233442/personId).
	 *
	 * @return actor string
	 */
	public String resolveActor();
}
