package edu.byu.auth.filter;

import edu.byu.security.userdetails.IdentityDetails;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Map;

/**
 * Author: Wyatt Taylor (wyatt_taylor@byu.edu)
 * Date: 05/10/2013
 *
 * @author Wyatt Taylor (wyatt_taylor@byu.edu)
 * @since 05/10/2013
 */
public class HeaderAuth extends AbstractAuthenticationToken implements Authentication {

	private IdentityDetails id;
	private String headerUsed;

	public HeaderAuth(final IdentityDetails id, final String headerUsed) {
		super(id.getAuthorities());
		this.id = id;
		this.headerUsed = headerUsed;
	}

	@Override
	public Object getCredentials() {
		return headerUsed;
	}

	@Override
	public Object getPrincipal() {
		return id;
	}
}
