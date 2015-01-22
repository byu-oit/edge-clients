package edu.byu.edge.filters.apacheHttp;

import edu.byu.auth.client.CredentialClient;
import edu.byu.edge.filters.ActorResolver;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;

/**
 * Created by wct5 on 1/21/15.
 */
public class HttpRequestAuthInterceptor implements HttpRequestInterceptor {

	private ActorResolver actorResolver;
	private CredentialClient credentialClient;

	public HttpRequestAuthInterceptor(final CredentialClient credentialClient) {
		this.credentialClient = credentialClient;
	}

	public void setActorResolver(final ActorResolver actorResolver) {
		this.actorResolver = actorResolver;
	}

	@Override
	public void process(final HttpRequest request, final HttpContext context) throws HttpException, IOException {
		request.addHeader("Authorization", getHeaderString());
	}

	private String getHeaderString() {
		final String actor = getActor();
		if (actorResolver == null || "".equals(actor)) {
			return credentialClient.obtainAuthorizationHeaderString();
		} else {
			return credentialClient.obtainAuthorizationHeaderString(actor);
		}
	}

	private String getActor() {
		if (actorResolver == null) return "";
		final String actor = actorResolver.resolveActor();
		return actor == null ? "" : actor;
	}
}
