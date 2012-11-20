package edu.byu.security.authentication;

import edu.byu.security.hmac.ws.authentication.principal.PrincipalCredential;
import org.springframework.security.core.Authentication;
import org.springframework.util.Assert;

/**
 * Author: Wyatt Taylor (wyatt_taylor@byu.edu)
 * Date: 11/13/2012
 *
 * @author Wyatt Taylor (wyatt_taylor@byu.edu)
 * @since 11/13/2012
 */
public class PrincipalAuthenticationRequest extends CredentialBasedAuthenticationRequest<PrincipalCredential> implements Authentication {

	private final PrincipalCredential credential;

	public PrincipalAuthenticationRequest(final PrincipalCredential credential) {
		super(credential);
		Assert.notNull(credential, "A credential is required to create an authentication request.");
		this.credential = credential;
	}

	@Override
	public Object getDetails() {
		return credential.getId();
	}

	@Override
	public String getPrincipal() {
		return credential.getPersonId();
	}

	@Override
	public String getName() {
		return credential.getPersonId();
	}
}
