package edu.byu.edge.accesstoken.interceptor;

import edu.byu.edge.accesstoken.AccessTokenClient;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

/**
 * Created by Scott Hutchings on 6/29/2016.
 */
public class BearerTokenHttpRequestInterceptor implements ClientHttpRequestInterceptor {
	private AccessTokenClient tokenClient;

	public BearerTokenHttpRequestInterceptor(AccessTokenClient tokenClient) {
		this.tokenClient = tokenClient;
	}

	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
		final HttpHeaders headers = request.getHeaders();
		try {
			headers.add("Authorization", "Bearer " + tokenClient.getAccessToken());
		} catch (OAuthSystemException e) {
			e.printStackTrace();
			throw new IOException(e);
		} catch (OAuthProblemException e) {
			e.printStackTrace();
			throw new IOException(e);
		}
		return execution.execute(request, body);
	}
}
