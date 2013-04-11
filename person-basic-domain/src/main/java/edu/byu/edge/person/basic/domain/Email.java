package edu.byu.edge.person.basic.domain;

/**
 * Created by IntelliJ IDEA.
 * User: thirschi
 * Date: 10/26/12
 * Time: 1:10 PM
 */
public class Email {

	/**
	 *
	 */
	private String personId;

	/**
	 *
	 */
	private String emailAddress;

	/**
	 *
	 */
	public Email() {
	}

	/**
	 *
	 * @param personId PersonId
	 * @param emailAddress EmailAddress
	 */
	public Email(String personId, String emailAddress) {
		this.personId = personId;
		this.emailAddress = emailAddress;
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
	public String getEmailAddress() {
		return emailAddress;
	}

	/**
	 *
	 * @param emailAddress EmailAddress
	 */
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
}
