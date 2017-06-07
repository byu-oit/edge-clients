package edu.byu.edge.coreIdentity.domain;

import java.util.Date;

/**
 * Created by Scott Hutchings on 2/3/2016.
 */
public class CoreIdentity {
	private String personId;
	private String netId;
	private String byuId;
	private String byuIdFormatted;
	private String preferredName;
	private String completeName;
	private Date dateOfBirth;
	private String gender;
	private String religion;
	private boolean isRestricted;
	private boolean isRegistrarWarning;
	private boolean isDeceased;
	private String studentRole;
	private String employRole;

//	private String fullName;
//	private String preferredFirstName;
//	private String restOfName;
//	private String surname;
//	private String preferredSurname;
//	private String sortName;
//	private String emailAddress;
//	private Boolean emailAddressUnlisted;


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

	public String getPreferredName() {
		return preferredName;
	}

	public void setPreferredName(String preferredName) {
		this.preferredName = preferredName;
	}

	public String getCompleteName() {
		return completeName;
	}

	public void setCompleteName(String completeName) {
		this.completeName = completeName;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getReligion() {
		return religion;
	}

	public void setReligion(String religion) {
		this.religion = religion;
	}

	public boolean isRestricted() {
		return isRestricted;
	}

	public void setRestricted(boolean restricted) {
		isRestricted = restricted;
	}

	public boolean isRegistrarWarning() {
		return isRegistrarWarning;
	}

	public void setRegistrarWarning(boolean registrarWarning) {
		isRegistrarWarning = registrarWarning;
	}

	public boolean isDeceased() {
		return isDeceased;
	}

	public void setDeceased(boolean deceased) {
		isDeceased = deceased;
	}

	public String getStudentRole() {
		return studentRole;
	}

	public void setStudentRole(String studentRole) {
		this.studentRole = studentRole;
	}

	public String getEmployRole() {
		return employRole;
	}

	public void setEmployRole(String employRole) {
		this.employRole = employRole;
	}

	@Override
	public String toString() {
		return "CoreIdentity{" +
				"personId='" + personId + '\'' +
				", netId='" + netId + '\'' +
				", byuId='" + byuId + '\'' +
				", byuIdFormatted='" + byuIdFormatted + '\'' +
				", preferredName='" + preferredName + '\'' +
				", completeName='" + completeName + '\'' +
				", dateOfBirth=" + dateOfBirth +
				", gender='" + gender + '\'' +
				", religion='" + religion + '\'' +
				", isRestricted=" + isRestricted +
				", isRegistrarWarning=" + isRegistrarWarning +
				", isDeceased=" + isDeceased +
				", studentRole='" + studentRole + '\'' +
				", employRole='" + employRole + '\'' +
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
