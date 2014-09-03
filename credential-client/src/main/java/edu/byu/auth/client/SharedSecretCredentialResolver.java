package edu.byu.auth.client;

import edu.byu.auth.domain.Credential;

/**
 * Author: Wyatt Taylor (wyatt_taylor@byu.edu)
 * Date: 09/02/2014
 *
 * @author Wyatt Taylor (wyatt_taylor@byu.edu)
 * @since 09/02/2014
 */
public interface SharedSecretCredentialResolver {

	public Credential getApiKeyCredential();
}
