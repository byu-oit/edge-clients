package edu.byu.auth.client.impl;

import edu.byu.auth.client.PasswordCredentialResolver;
import org.springframework.util.Assert;

/**
 * Author: Wyatt Taylor (wyatt_taylor@byu.edu)
 * Date: 09/03/2014
 *
 * @author Wyatt Taylor (wyatt_taylor@byu.edu)
 * @since 09/03/2014
 */
public class PasswordFileCredentialResolver extends AbstractFileCredentialResolver implements PasswordCredentialResolver {

	public static final String USERNAME_KEY = "username";
	public static final String PASSWORD_KEY = "password";

	public PasswordFileCredentialResolver() {
	}

	@Override
	protected void myAfterPropertiesSet() {
		Assert.isTrue(props.containsKey(USERNAME_KEY), "Credential file does not contain a username.");
		Assert.isTrue(props.containsKey(PASSWORD_KEY), "Credential file does not contain a password.");
	}

	@Override
	public String getUsername() {
		return props.get(USERNAME_KEY);
	}

	@Override
	public String getPassword() {
		return decrypt(props.get(PASSWORD_KEY));
	}
}
