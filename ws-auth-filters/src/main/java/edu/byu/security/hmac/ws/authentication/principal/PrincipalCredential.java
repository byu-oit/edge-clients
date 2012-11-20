package edu.byu.security.hmac.ws.authentication.principal;

import edu.byu.security.Credential;

import java.io.Serializable;
import java.util.Date;

/**
 * Author: Wyatt Taylor (wyatt_taylor@byu.edu)
 * Date: 11/13/2012
 *
 * @author Wyatt Taylor (wyatt_taylor@byu.edu)
 * @since 11/13/2012
 */
public class PrincipalCredential implements Credential, Serializable {

	private static final long serialVersionUID = 1L;

	public static final String SM_USER = "SM_USER";
	public static final String ACTOR_ID = "ACTOR_ID";
	public static final String PRINCIPAL_ID = "PRINCIPAL_ID";
	public static final String WS_AUTH_TYPE = PRINCIPAL_ID;

	private final String principalId;

	public PrincipalCredential(final String principalId) {
		this.principalId = principalId;
	}

	@Override
	public String getPersonId() {
		return principalId;
	}

	@Override
	public String getId() {
		return principalId;
	}

	@Override
	public String getValue() {
		return null;
	}

	@Override
	public boolean isExpired() {
		return false;
	}

	@Override
	public void expire() {
	}

	@Override
	public Date getExpirationDate() {
		return null;
	}

	@Override
	public boolean isDisabled() {
		return false;
	}

	@Override
	public void disable() {
	}
}
