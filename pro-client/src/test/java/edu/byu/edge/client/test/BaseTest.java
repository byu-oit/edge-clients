package edu.byu.edge.client.test;

import edu.byu.security.userdetails.IdentityDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;
import java.util.Collections;

/**
 * Author: Wyatt Taylor (wyatt_taylor@byu.edu)
 * Date: 8/7/12
 *
 * @author Wyatt Taylor (wyatt_taylor@byu.edu)
 * @since 8/7/12
 */
public abstract class BaseTest {

	/**
	 * @param netId the netId
	 * @param personId the personId
	 * @param name the name
	 * @param byuId the byuId
	 * @param email the email
	 */
	protected void setActor(final String netId, final String personId, final String name, final String byuId, final String email) {
		final IdentityDetails details = new IdentityDetails(personId, netId, byuId, name, email, Collections.<GrantedAuthority>emptySet());
		final Authentication auth = new Authentication() {
			@Override
			public Collection<GrantedAuthority> getAuthorities() {
				return details.getAuthorities();
			}

			@Override
			public Object getCredentials() {
				return null;
			}

			@Override
			public Object getDetails() {
				return null;
			}

			@Override
			public Object getPrincipal() {
				return details;
			}

			@Override
			public boolean isAuthenticated() {
				return true;
			}

			@Override
			public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

			}

			@Override
			public String getName() {
				return details.getName();
			}
		};
		SecurityContextHolder.getContext().setAuthentication(auth);
	}
}
