package edu.byu.edge.accesstoken;

import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;

/**
 * Created by Scott Hutchings on 6/28/2016.
 */
public interface AccessTokenClient {

	public String getAccessToken() throws OAuthSystemException, OAuthProblemException;
}
