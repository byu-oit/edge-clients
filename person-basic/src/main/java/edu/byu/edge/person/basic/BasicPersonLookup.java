package edu.byu.edge.person.basic;

import edu.byu.edge.person.basic.domain.BasicPerson;

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

	/**
	 * Gets BasicPerson for a netId
	 *
	 * @param netId NetId
	 * @return result
	 */
	BasicPerson getPersonByNetId(final String netId);

	/**
	 *
	 * Gets BasicPerson for a byuId
	 *
	 * @param byuId ByuId
	 * @return result
	 */
	BasicPerson getPersonByByuId(final String byuId);

	BasicPerson getPersonBySsn(String ssn);

	List<BasicPerson> searchBy(String searchParam);
}
