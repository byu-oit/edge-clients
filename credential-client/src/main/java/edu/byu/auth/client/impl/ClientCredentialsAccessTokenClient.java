package edu.byu.auth.client.impl;

import edu.byu.auth.client.AccessTokenClient;
import edu.byu.auth.domain.AccessToken;
import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthJSONAccessTokenResponse;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by Scott Hutchings on 8/29/2016.
 */
public class ClientCredentialsAccessTokenClient implements AccessTokenClient {

	private String clientId;
	private String clientSecret;
	private String tokenUrl;

	private AtomicReference<AccessToken> accessToken = new AtomicReference<AccessToken>();

	public ClientCredentialsAccessTokenClient(String clientId, String clientSecret, String tokenUrl) {
		this.clientId = clientId;
		this.clientSecret = clientSecret;
		this.tokenUrl = tokenUrl;
	}

	@Override
	public String obtainAuthorizationHeaderString() {
		return "Bearer " + getAccessToken().getToken();
	}

	@Override
	public AccessToken getAccessToken() {
		if (accessToken.get() == null || System.currentTimeMillis() >= accessToken.get().getExpires()){
			resetAccessToken();
		}
		return accessToken.get();
	}

	private void resetAccessToken() {
		try {
			OAuthClient client = new OAuthClient(new URLConnectionClient());
			OAuthClientRequest request =  OAuthClientRequest.tokenLocation(tokenUrl)
					.setGrantType(GrantType.CLIENT_CREDENTIALS)
					.setClientId(clientId)
					.setClientSecret(clientSecret)
					.buildQueryMessage();

			final OAuthJSONAccessTokenResponse response = client.accessToken(request, OAuthJSONAccessTokenResponse.class);

			AccessToken nextAccessToken = new AccessToken(AccessToken.TokenType.ACCESS_TOKEN);
			nextAccessToken.setToken(response.getAccessToken());
			final Long expiresInSeconds = response.getExpiresIn();

			nextAccessToken.setExpires(System.currentTimeMillis() + (expiresInSeconds * 1000));

			accessToken.set(nextAccessToken);
		} catch (OAuthSystemException e) {
			throw new RuntimeException(e);
		} catch (OAuthProblemException e) {
			throw new RuntimeException(e);
		}
	}
}
