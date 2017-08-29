package edu.byu.edge.coreIdentity.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * Created by Scott Hutchings on 8/30/2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class IdentityLookupSummary {
	private String sortName;
	private String jobTitle;
	private String department;
	private String homeTown;
	private String homeStateCode;
	@JsonDeserialize(using = StringToBooleanDeserializer.class)
	private Boolean registrarWarning;
	private String personId;
	private String byuId;
	private String netId;
	private String dateOfBirth;
	private String gender;
	private String employeeStatus;
	private String emailService;
	@JsonProperty("Home Address Block")
	private AddressBlock homeAddressBlock;
	@JsonProperty("Permanent Address Block")
	private AddressBlock permanentAddressBlock;
	@JsonProperty("Campus Address Block")
	private AddressBlock campusAddressBlock;

	public String getSortName() {
		return sortName;
	}

	public void setSortName(String sortName) {
		this.sortName = sortName;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getHomeTown() {
		return homeTown;
	}

	public void setHomeTown(String homeTown) {
		this.homeTown = homeTown;
	}

	public String getHomeStateCode() {
		return homeStateCode;
	}

	public void setHomeStateCode(String homeStateCode) {
		this.homeStateCode = homeStateCode;
	}

	public Boolean getRegistrarWarning() {
		return registrarWarning;
	}

	public void setRegistrarWarning(Boolean registrarWarning) {
		this.registrarWarning = registrarWarning;
	}

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public String getByuId() {
		return byuId;
	}

	public void setByuId(String byuId) {
		this.byuId = byuId;
	}

	public String getNetId() {
		return netId;
	}

	public void setNetId(String netId) {
		this.netId = netId;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getEmployeeStatus() {
		return employeeStatus;
	}

	public void setEmployeeStatus(String employeeStatus) {
		this.employeeStatus = employeeStatus;
	}

	public String getEmailService() {
		return emailService;
	}

	public void setEmailService(String emailService) {
		this.emailService = emailService;
	}

	public AddressBlock getHomeAddressBlock() {
		return homeAddressBlock;
	}

	public void setHomeAddressBlock(AddressBlock homeAddressBlock) {
		this.homeAddressBlock = homeAddressBlock;
	}

	public AddressBlock getPermanentAddressBlock() {
		return permanentAddressBlock;
	}

	public void setPermanentAddressBlock(AddressBlock permanentAddressBlock) {
		this.permanentAddressBlock = permanentAddressBlock;
	}

	public AddressBlock getCampusAddressBlock() {
		return campusAddressBlock;
	}

	public void setCampusAddressBlock(AddressBlock campusAddressBlock) {
		this.campusAddressBlock = campusAddressBlock;
	}

	@Override
	public String toString() {
		return "IdentityLookupSummary{" +
				"sortName='" + sortName + '\'' +
				", jobTitle='" + jobTitle + '\'' +
				", department='" + department + '\'' +
				", homeTown='" + homeTown + '\'' +
				", homeStateCode='" + homeStateCode + '\'' +
				", registrarWarning=" + registrarWarning +
				", personId='" + personId + '\'' +
				", byuId='" + byuId + '\'' +
				", netId='" + netId + '\'' +
				", dateOfBirth='" + dateOfBirth + '\'' +
				", gender='" + gender + '\'' +
				", employeeStatus='" + employeeStatus + '\'' +
				", emailService='" + emailService + '\'' +
				", homeAddressBlock=" + homeAddressBlock +
				", permanentAddressBlock=" + permanentAddressBlock +
				", campusAddressBlock=" + campusAddressBlock +
				'}';
	}
}
