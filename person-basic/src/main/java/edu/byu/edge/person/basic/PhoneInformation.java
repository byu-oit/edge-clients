package edu.byu.edge.person.basic;

/**
 * Created by IntelliJ IDEA.
 * User: thirschi
 * Date: 10/10/12
 * Time: 9:55 AM
 */
public class PhoneInformation {

	/**
	 *
	 */
	private String personId;

	/**
	 *
	 */
	private PhoneType phoneType;

	/**
	 *
	 */
	private String phoneNumber;

	/**
	 *
	 */
	private TypeOfDevice typeOfDevice;

	/**
	 *
	 */
	private String countryCode;

	/**
	 *
	 */
	private String mobile;

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
	private Boolean primaryF;

	/**
	 *
	 */
	public PhoneInformation() {
	}

	/**
	 *
	 * @param personId PersonId
	 * @param phoneType PhoneType
	 * @param phoneNumber PhoneNumber
	 * @param typeOfDevice TypeOfDevice
	 * @param countryCode CountryCode
	 * @param mobile Mobile
	 * @param contactStatus ContactStatus
	 * @param unlisted UnListed
	 * @param primaryF PrimaryF
	 */
	public PhoneInformation(String personId, String phoneType, String phoneNumber, String typeOfDevice, String countryCode, String mobile, String contactStatus, String unlisted, String primaryF) {
		this.personId = personId;
		if(phoneType.equals("MAL")){
			this.phoneType = PhoneType.MAL;
		} else if(phoneType.equals("PRM")){
			this.phoneType = PhoneType.PRM;
		} else if(phoneType.equals("EMR")){
			this.phoneType = PhoneType.EMR;
		} else if(phoneType.equals("RES")){
			this.phoneType = PhoneType.RES;
		} else if(phoneType.equals("WRK")){
			this.phoneType = PhoneType.WRK;
		} else if(phoneType.equals("EMA")){
			this.phoneType = PhoneType.EMA;
		} else {
			this.phoneType = PhoneType.OTH;
		}
		this.phoneNumber = phoneNumber;
		if(typeOfDevice.equals("FAX")){
			this.typeOfDevice = TypeOfDevice.FAX;
		} else if(typeOfDevice.equals("PHONE")){
			this.typeOfDevice = TypeOfDevice.PHONE;
		} else if(typeOfDevice.equals("PAGER")){
			this.typeOfDevice = TypeOfDevice.PAGER;
		} else {
			this.typeOfDevice = TypeOfDevice.OTHER;
		}
		this.countryCode = countryCode;
		this.mobile = mobile;
		this.contactStatus = contactStatus;
		this.unlisted = unlisted;
		if(primaryF.equals("Y")){
			this.primaryF = true;
		} else {
			this.primaryF = false;
		}
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
	public PhoneType getPhoneType() {
		return phoneType;
	}

	/**
	 *
	 * @param phoneType PhoneType
	 */
	public void setPhoneType(PhoneType phoneType) {
		this.phoneType = phoneType;
	}

	/**
	 *
	 * @return result
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 *
	 * @param phoneNumber PhoneNumber
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/**
	 *
	 * @return result
	 */
	public TypeOfDevice getTypeOfDevice() {
		return typeOfDevice;
	}

	/**
	 *
	 * @param typeOfDevice TypeOfDevice
	 */
	public void setTypeOfDevice(TypeOfDevice typeOfDevice) {
		this.typeOfDevice = typeOfDevice;
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
	public String getMobile() {
		return mobile;
	}

	/**
	 *
	 * @param mobile Mobile
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
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
	public Boolean getPrimaryF() {
		return primaryF;
	}

	/**
	 *
	 * @param primaryF PrimaryF
	 */
	public void setPrimaryF(Boolean primaryF) {
		this.primaryF = primaryF;
	}
}
