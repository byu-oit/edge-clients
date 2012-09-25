package edu.byu.edge.person.basic;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: thirschi
 * Date: 9/24/12
 * Time: 3:17 PM
 */
public interface BasicPersonLookup {

	/**
	 *
	 * Get all the BasicPerson for all personIds in the list
	 *
	 * @param personIds PersonIds
	 * @return result
	 */
	List<BasicPerson> getPersonsByListPersonIds(final List<String> personIds);

	/**
	 *
	 * Gets BasicPerson for a personId
	 *
	 * @param personId PersonId
	 * @return result
	 */
	BasicPerson getPersonByPersonId(final String personId);
}
