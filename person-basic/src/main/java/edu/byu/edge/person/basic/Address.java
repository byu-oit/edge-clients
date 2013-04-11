package edu.byu.edge.person.basic;

/**
 * Created by IntelliJ IDEA.
 * User: thirschi
 * Date: 10/10/12
 * Time: 12:23 PM
 */
public class Address {

	/**
	 *
	 */
	private String personId;

	/**
	 *
	 */
	private AddressType addressType;

	/**
	 *
	 */
	private String line1;

	/**
	 *
	 */
	private String line2;

	/**
	 *
	 */
	private String line3;

	/**
	 *
	 */
	private String line4;

	/**
	 *
	 */
	private String countryCode;

	/**
	 *
	 */
	private String city;

	/**
	 *
	 */
	private String stateCode;

	/**
	 *
	 */
	private String postalCode;

	/**
	 *
	 */
	private String contactStatus;

	/**
	 *
	 */
	private String unlisted;

	/**
	 *
	 */
	private String campusAddressF;

	/**
	 *
	 */
	public Address() {
	}

	/**
	 *
	 * @param personId PersonId
	 * @param addressType AddressType
	 * @param line1 Line1
	 * @param line2 Line2
	 * @param line3 Line3
	 * @param line4 Line4
	 * @param countryCode CountryCode
	 * @param city City
	 * @param stateCode StateCode
	 * @param postalCode PostalCode
	 * @param contactStatus ContactStatus
	 * @param unlisted UnListed
	 * @param campusAddressF CampusAddressF
	 */
	public Address(String personId, String addressType, String line1, String line2, String line3, String line4, String countryCode, String city, String stateCode, String postalCode, String contactStatus, String unlisted, String campusAddressF) {
		this.personId = personId;
		setAddressType(addressType);
		this.line1 = line1;
		this.line2 = line2;
		this.line3 = line3;
		this.line4 = line4;
		this.countryCode = countryCode;
		this.city = city;
		this.stateCode = stateCode;
		this.postalCode = postalCode;
		this.contactStatus = contactStatus;
		this.unlisted = unlisted;
		this.campusAddressF = campusAddressF;
	}

	/**
	 *
	 * @return result
	 */
	public String getPersonId() {
		return personId;
	}

	/**
	 *
	 * @param personId PersonId
	 */
	public void setPersonId(String personId) {
		this.personId = personId;
	}

	/**
	 *
	 * @return result
	 */
	public AddressType getAddressType() {
		return addressType;
	}

	/**
	 *
	 * @param addressType AddressType
	 */
	public void setAddressType(String addressType) {
		if(addressType.equals("PRM")){
			this.addressType = AddressType.PRM;
		} else if (addressType.equals("MAL")){
			this.addressType = AddressType.MAL;
		} else if (addressType.equals("RES")){
			this.addressType = AddressType.RES;
		} else if (addressType.equals("WRK")){
			this.addressType = AddressType.WRK;
		} else {
			this.addressType = AddressType.OTH;
		}
	}

	/**
	 *
	 * @return result
	 */
	public String getLine1() {
		return line1;
	}

	/**
	 *
	 * @param line1 Line1
	 */
	public void setLine1(String line1) {
		this.line1 = line1;
	}

	/**
	 *
	 * @return result
	 */
	public String getLine2() {
		return line2;
	}

	/**
	 *
	 * @param line2 Line2
	 */
	public void setLine2(String line2) {
		this.line2 = line2;
	}

	/**
	 *
	 * @return result
	 */
	public String getLine3() {
		return line3;
	}

	/**
	 *
	 * @param line3 Line3
	 */
	public void setLine3(String line3) {
		this.line3 = line3;
	}

	/**
	 *
	 * @return result
	 */
	public String getLine4() {
		return line4;
	}

	/**
	 *
	 * @param line4 Line4
	 */
	public void setLine4(String line4) {
		this.line4 = line4;
	}

	/**
	 *
	 * @return result
	 */
	public String getCountryCode() {
		return countryCode;
	}

	/**
	 *
	 * @param countryCode CountryCode
	 */
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	/**
	 *
	 * @return result
	 */
	public String getCity() {
		return city;
	}

	/**
	 *
	 * @param city City
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 *
	 * @return result
	 */
	public String getStateCode() {
		return stateCode;
	}

	/**
	 *
	 * @param stateCode StateCode
	 */
	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	/**
	 *
	 * @return result
	 */
	public String getPostalCode() {
		return postalCode;
	}

	/**
	 *
	 * @param postalCode PostalCode
	 */
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	/**
	 *
	 * @return result
	 */
	public String getContactStatus() {
		return contactStatus;
	}

	/**
	 *
	 * @param contactStatus ContactStatus
	 */
	public void setContactStatus(String contactStatus) {
		this.contactStatus = contactStatus;
	}

	/**
	 *
	 * @return result
	 */
	public String getUnlisted() {
		return unlisted;
	}

	/**
	 *
	 * @param unlisted UnListed
	 */
	public void setUnlisted(String unlisted) {
		this.unlisted = unlisted;
	}

	/**
	 *
	 * @return result
	 */
	public String getCampusAddressF() {
		return campusAddressF;
	}

	/**
	 *
	 * @param campusAddressF CampusAddressF
	 */
	public void setCampusAddressF(String campusAddressF) {
		this.campusAddressF = campusAddressF;
	}
}
