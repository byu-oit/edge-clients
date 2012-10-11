package edu.byu.edge.person.basic;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: thirschi
 * Date: 10/10/12
 * Time: 12:18 PM
 */
public interface AddressLookup {

	/**
	 *
	 * @param personId PersonId
	 * @return result
	 */
	List<Address> getAddressByPersonId(final String personId);

	/**
	 *
	 * @param personId PersonId
	 * @param addressType AddressType
	 * @return result
	 */
	Address getAddressByPersonIdAndType(final String personId, final AddressType addressType);
}
