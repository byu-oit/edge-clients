package edu.byu.edge.person.basic.domain;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: thirschi
 * Date: 9/24/12
 * Time: 3:19 PM
 */
public class BasicPerson {

	/**
	 *
	 */
	private final String personId;

	/**
	 *
	 */
	private final String netId;

	/**
	 *
	 */
	private final String restOfName;

	/**
	 *
	 */
	private final String preferredFirstName;

	/**
	 *
	 */
	private final String surname;

	/**
	 *
	 */
	private final String byuId;

	/**
	 *
	 */
	private final Date birthDate;

	/**
	 *
	 */
	private final String gender;

	/**
	 *
	 */
	private final Boolean organization;

	/**
	 *
	 */
	private final String religionCode;

	/**
	 *
	 */
	private final String ssn;
	
	public BasicPerson() {
		this.netId = null;
		this.restOfName = null;
		this.preferredFirstName = null;
		this.surname = null;
		this.personId = null;
		this.byuId = null;
		this.birthDate = null;
		this.gender = null;
		this.organization = null;
		this.religionCode = null;
		this.ssn = null;
	}

	/**
	 *
	 * @param personId PersonId
	 * @param netId NetId
	 * @param restOfName RestOfName
	 * @param preferredFirstName PreferredFirstName
	 * @param surname Surname
	 * @param byuId ByuId
	 * @param birthDate BirthDate
	 * @param gender Gender
	 * @param ssn SSN
	 */
	public BasicPerson(String personId, String netId, String restOfName, String preferredFirstName, String surname, String byuId, Date birthDate, String gender, String organization, String religionCode, String ssn) {
		this.netId = netId;
		this.restOfName = restOfName;
		this.preferredFirstName = preferredFirstName;
		this.surname = surname;
		this.personId = personId;
		this.byuId = byuId;
		this.birthDate = birthDate;
		this.gender = gender;
		this.organization = (!(organization == null || organization.trim().isEmpty() || organization.trim().equalsIgnoreCase("N")));
		this.religionCode = religionCode;
		this.ssn = ssn;
	}

	public String getPreferredFirstName() {
		return preferredFirstName;
	}

	public String getSurname() {
		return surname;
	}

	public String getNetId() {
		return netId;
	}

	public String getRestOfName() {
		return restOfName;
	}

	public String getName() {
		return ((preferredFirstName != null) ? preferredFirstName : restOfName) + " " + surname;
	}

	public String getPersonId() {
		return personId;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public String getByuId() {
		return byuId;
	}

	public String getGender() {
		return gender;
	}

	public Boolean getOrganization() {
		return organization;
	}

	public String getReligionCode() {
		return religionCode;
	}

	public String getSsn() {
		return ssn;
	}
}
