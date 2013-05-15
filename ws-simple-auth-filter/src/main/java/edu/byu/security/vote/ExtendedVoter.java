package edu.byu.security.vote;

import org.springframework.security.access.AccessDecisionVoter;

/**
 * The ExtendedVoter provides additional methods that can be used to test
 * authorization in contexts other than those provided by the AccessDecisionVoter.
 *
 * @author Andrew Largey
 *
 */
public interface ExtendedVoter extends AccessDecisionVoter {

	/**
	 * Determines if the current principal as the given permission.
	 * @param permission String permission to evaluate.
	 * @return boolean true if they have the permission, false otherwise.
	 */
	boolean isAuthorized(String permission);

	/**
	 * Checks if the current principal has the given permission.
	 * <p/>
	 * Same thing as isAuthorized, but instead of returning true or false
	 * Will throw an AccessDeniedException if not authorized
	 * @param permission String permission string to check.
	 */
	void checkAuthorized(String permission);
}
