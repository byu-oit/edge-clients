package edu.byu.edge.coreIdentity.domain;

/**
 * Created by Scott Hutchings on 2/3/2016.
 */
public class CoreIdentity {
	private String personId;
	private String netId;
	private String byuId;
	private String byuIdFormatted;
	private String name;
	private String fullName;
	private String preferredFirstName;
	private String restOfName;
	private String surname;
	private String preferredSurname;
	private String sortName;
	private String emailAddress;
	private Boolean emailAddressUnlisted;
	private String gender;
	private String isRestricted;

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public String getNetId() {
		return netId;
	}

	public void setNetId(String netId) {
		this.netId = netId;
	}

	public String getByuId() {
		return byuId;
	}

	public void setByuId(String byuId) {
		this.byuId = byuId;
	}

	public String getByuIdFormatted() {
		return byuIdFormatted;
	}

	public void setByuIdFormatted(String byuIdFormatted) {
		this.byuIdFormatted = byuIdFormatted;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getPreferredFirstName() {
		return preferredFirstName;
	}

	public void setPreferredFirstName(String preferredFirstName) {
		this.preferredFirstName = preferredFirstName;
	}

	public String getRestOfName() {
		return restOfName;
	}

	public void setRestOfName(String restOfName) {
		this.restOfName = restOfName;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getPreferredSurname() {
		return preferredSurname;
	}

	public void setPreferredSurname(String preferredSurname) {
		this.preferredSurname = preferredSurname;
	}

	public String getSortName() {
		return sortName;
	}

	public void setSortName(String sortName) {
		this.sortName = sortName;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public Boolean getEmailAddressUnlisted() {
		return emailAddressUnlisted;
	}

	public void setEmailAddressUnlisted(Boolean emailAddressUnlisted) {
		this.emailAddressUnlisted = emailAddressUnlisted;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getIsRestricted() {
		return isRestricted;
	}

	public void setIsRestricted(String isRestricted) {
		this.isRestricted = isRestricted;
	}

	@Override
	public String toString() {
		return "CoreIdentity{" +
				"personId='" + personId + '\'' +
				", netId='" + netId + '\'' +
				", byuId='" + byuId + '\'' +
				", byuIdFormatted='" + byuIdFormatted + '\'' +
				", name='" + name + '\'' +
				", fullName='" + fullName + '\'' +
				", preferredFirstName='" + preferredFirstName + '\'' +
				", restOfName='" + restOfName + '\'' +
				", surname='" + surname + '\'' +
				", preferredSurname='" + preferredSurname + '\'' +
				", sortName='" + sortName + '\'' +
				", emailAddress='" + emailAddress + '\'' +
				", emailAddressUnlisted=" + emailAddressUnlisted +
				", gender='" + gender + '\'' +
				", isRestricted='" + isRestricted + '\'' +
				'}';
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) return true;
		if (!(o instanceof CoreIdentity)) return false;

		CoreIdentity identity = (CoreIdentity) o;

		if (personId != null ? !personId.equals(identity.personId) : identity.personId != null) return false;
		if (netId != null ? !netId.equals(identity.netId) : identity.netId != null) return false;
		return byuId != null ? byuId.equals(identity.byuId) : identity.byuId == null;

	}

	@Override
	public int hashCode() {
		int result = personId != null ? personId.hashCode() : 0;
		result = 31 * result + (netId != null ? netId.hashCode() : 0);
		result = 31 * result + (byuId != null ? byuId.hashCode() : 0);
		return result;
	}
}
