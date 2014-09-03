package edu.byu.auth.client;

/**
 * Author: Wyatt Taylor (wyatt_taylor@byu.edu)
 * Date: 09/02/2014
 *
 * @author Wyatt Taylor (wyatt_taylor@byu.edu)
 * @since 09/02/2014
 */
public interface PasswordCredentialResolver {

	public String getUsername();

	public String getPassword();
}
