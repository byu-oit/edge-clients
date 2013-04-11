package edu.byu.edge.person.basic;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: thirschi
 * Date: 4/9/13
 * Time: 11:31 AM
 */
public interface PersonAddressLookup {
	/**
	 *
	 * @param nameMask can also be a byu-id/ssn/net-id mask
	 * @param addressLine1Mask
	 * @param cityMask
	 * @param stateMask
	 * @param zipCodeMask
	 * @return a list containing all possible matches that include all supplied arguments
	 */
	public List<PersonAddress> performAddressSearch(String nameMask, String addressLine1Mask, String cityMask, String stateMask, String zipCodeMask);

	/**
	 *
	 * @param nameMask can also be a byu-id/ssn/net-id mask
	 * @param addressLine1Mask
	 * @param cityMask
	 * @param stateMask
	 * @param zipCodeMask
	 * @return a list containing all possible matches that include all supplied arguments
	 */
	public List<PersonAddress> performAddressSearchOptimized(
			String nameMask, String addressLine1Mask, String cityMask, String stateMask, String zipCodeMask
	);
}
