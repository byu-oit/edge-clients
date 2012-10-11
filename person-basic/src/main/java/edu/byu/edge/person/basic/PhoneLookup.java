package edu.byu.edge.person.basic;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: thirschi
 * Date: 10/10/12
 * Time: 10:07 AM
 */
public interface PhoneLookup {

	/**
	 *
	 * @param personId PersonId
	 * @return result
	 */
	List<PhoneInformation> getPhoneInformationByPersonId(final String personId);

	/**
	 *
	 * @param personId PersonId
	 * @param phoneType PhoneType
	 * @return result
	 */
	PhoneInformation getPhoneInformationByPersonIdAndType(final String personId, final PhoneType phoneType);
}
