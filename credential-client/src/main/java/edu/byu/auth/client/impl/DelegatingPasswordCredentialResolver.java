package edu.byu.auth.client.impl;

import edu.byu.auth.client.PasswordCredentialResolver;
import org.springframework.beans.factory.InitializingBean;

import java.util.List;

/**
 * Created by wct5 on 3/23/16.
 */
public class DelegatingPasswordCredentialResolver implements InitializingBean, PasswordCredentialResolver {

	private List<PasswordCredentialResolver> resolvers;

	private String username;
	private String password;

	public DelegatingPasswordCredentialResolver() {
	}

	public void setResolvers(final List<PasswordCredentialResolver> resolvers) {
		this.resolvers = resolvers;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		final String[] s = resolveCredentials();
		if (s == null) throw new IllegalStateException("Unable to resolve credentials.");
		this.username = s[0];
		this.password = s[1];
	}

	private String[] resolveCredentials() {
		for (final PasswordCredentialResolver r : resolvers) {
			final String u = r.getUsername();
			final String p = r.getPassword();
			if (u != null && p != null) return new String[] {u, p};
		}
		return null;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	@Override
	public String getPassword() {
		return this.password;
	}
}
