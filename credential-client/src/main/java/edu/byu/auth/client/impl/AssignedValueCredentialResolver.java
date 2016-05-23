package edu.byu.auth.client.impl;

import edu.byu.auth.client.PasswordCredentialResolver;
import edu.byu.auth.client.SharedSecretCredentialResolver;
import edu.byu.auth.domain.Credential;
import org.springframework.beans.factory.InitializingBean;

/**
 * Created by wct5 on 3/23/16.
 */
public class AssignedValueCredentialResolver implements SharedSecretCredentialResolver, PasswordCredentialResolver {

	private String username;
	private String password;

	private Credential credential;

	public AssignedValueCredentialResolver() {
	}

	public AssignedValueCredentialResolver(final String username, final String password) {
		this.username = username;
		this.password = password;
	}

	public AssignedValueCredentialResolver(final Credential credential) {
		this.credential = credential;
	}

	public void setUsername(final String username) {
		this.username = username;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public void setCredential(final Credential credential) {
		this.credential = credential;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public Credential getApiKeyCredential() {
		return this.credential;
	}
}
