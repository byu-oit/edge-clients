package edu.byu.edge.person.basic;

/**
 * Created by IntelliJ IDEA.
 * User: thirschi
 * Date: 10/26/12
 * Time: 1:12 PM
 */
public interface EmailAddressLookup {

	/**
	 *
	 * Get Email Object for a person
	 *
	 * @param personId PersonId
	 * @return result
	 */
	Email getByPersonId(String personId);
}
