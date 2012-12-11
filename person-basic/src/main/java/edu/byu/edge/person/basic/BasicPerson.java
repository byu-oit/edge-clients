package edu.byu.edge.person.basic;

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
	 * @param personId PersonId
	 * @param netId NetId
	 * @param restOfName RestOfName
	 * @param preferredFirstName PreferredFirstName
	 * @param surname Surname
	 * @param byuId ByuId
	 * @param birthDate BirthDate
	 * @param gender Gender
	 */
	public BasicPerson(String personId, String netId, String restOfName, String preferredFirstName, String surname, String byuId, Date birthDate, String gender) {
		this.netId = netId;
		this.restOfName = restOfName;
		this.preferredFirstName = preferredFirstName;
		this.surname = surname;
		this.personId = personId;
		this.byuId = byuId;
		this.birthDate = birthDate;
		this.gender = gender;
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
}
