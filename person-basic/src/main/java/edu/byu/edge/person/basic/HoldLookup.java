package edu.byu.edge.person.basic;

/**
 * Created by IntelliJ IDEA.
 * User: thirschi
 * Date: 9/25/12
 * Time: 9:43 AM
 */
public interface HoldLookup {

	/**
	 *
	 * Check to see if someone has a type of hold
	 *
	 * @param personId PersonId
	 * @param flagName FlagName
	 * @param flagValue FlagValue
	 * @return result
	 */
	boolean hasHold(String personId, String flagName, String... flagValue);
}
