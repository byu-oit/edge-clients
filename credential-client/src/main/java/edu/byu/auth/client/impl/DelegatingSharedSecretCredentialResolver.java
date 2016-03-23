package edu.byu.auth.client.impl;

import edu.byu.auth.client.SharedSecretCredentialResolver;
import edu.byu.auth.domain.Credential;
import org.springframework.beans.factory.InitializingBean;

import java.util.List;

/**
 * Created by wct5 on 3/23/16.
 */
public class DelegatingSharedSecretCredentialResolver implements InitializingBean, SharedSecretCredentialResolver {

	private List<SharedSecretCredentialResolver> resolvers;

	private Credential credential;

	public DelegatingSharedSecretCredentialResolver() {
	}

	public void setResolvers(final List<SharedSecretCredentialResolver> resolvers) {
		this.resolvers = resolvers;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		this.credential = resolveCredential();
		if (credential == null) throw new IllegalStateException("Unable to locate a credential.");
	}

	private Credential resolveCredential() {
		for (final SharedSecretCredentialResolver r : resolvers) {
			final Credential c = r.getApiKeyCredential();
			if (c != null) return c;
		}
		return null;
	}

	@Override
	public Credential getApiKeyCredential() {
		return this.credential;
	}
}
