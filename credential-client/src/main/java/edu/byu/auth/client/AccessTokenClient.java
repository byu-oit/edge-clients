package edu.byu.auth.client;

import edu.byu.auth.domain.AccessToken;

/**
 * Created by Scott Hutchings on 8/29/2016.
 */
public interface AccessTokenClient extends WebAuthorizationClient {

	public AccessToken getAccessToken();
}
