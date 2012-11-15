package edu.byu.edge.client.apikey.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * Author: Wyatt Taylor (wyatt_taylor@byu.edu)
 * Date: 11/14/2012
 *
 * @author Wyatt Taylor (wyatt_taylor@byu.edu)
 * @since 11/14/2012
 */
public class SharedSecret implements Serializable {

	private static final long serialVersionUID = 1l;

	protected int credentialId;
	protected String personId;
	protected Date expirationDate;
	protected String sharedSecretValue;
	protected String wsid;
	protected String name;

	/** */
	public SharedSecret() {
	}

	/**
	 *
	 * @param credentialId
	 * @param personId
	 * @param expirationDate
	 * @param sharedSecretValue
	 * @param wsid
	 * @param name
	 */
	public SharedSecret(final int credentialId,
			final String personId,
			final Date expirationDate,
			final String sharedSecretValue,
			final String wsid,
			final String name) {
		this.credentialId = credentialId;
		this.personId = personId;
		this.expirationDate = expirationDate;
		this.sharedSecretValue = sharedSecretValue;
		this.wsid = wsid;
		this.name = name;
	}

	/** @return result */
	public int getCredentialId() {
		return credentialId;
	}

	/** @param credentialId value */
	public void setCredentialId(final int credentialId) {
		this.credentialId = credentialId;
	}

	/** @return result */
	public String getPersonId() {
		return personId;
	}

	/** @param personId value */
	public void setPersonId(final String personId) {
		this.personId = personId;
	}

	/** @return result */
	public Date getExpirationDate() {
		return expirationDate;
	}

	/** @param expirationDate value */
	public void setExpirationDate(final Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	/** @return result */
	public String getSharedSecretValue() {
		return sharedSecretValue;
	}

	/** @param sharedSecretValue value */
	public void setSharedSecretValue(final String sharedSecretValue) {
		this.sharedSecretValue = sharedSecretValue;
	}

	/** @return result */
	public String getWsid() {
		return wsid;
	}

	/** @param wsid value */
	public void setWsid(final String wsid) {
		this.wsid = wsid;
	}

	/** @return result */
	public String getName() {
		return name;
	}

	/** @param name value */
	public void setName(final String name) {
		this.name = name;
	}
}
