package edu.byu.edge.filters.jersey;

import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientRequest;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.filter.ClientFilter;
import edu.byu.auth.client.CredentialClient;
import edu.byu.edge.filters.ActorResolver;
import edu.byu.edge.filters.OutboundAuthFilter;

/**
 * Created by wct5 on 11/12/14.
 */
public class JerseyOutboundAuthFilter extends ClientFilter implements OutboundAuthFilter {

	private ActorResolver actorResolver;
	private CredentialClient credentialClient;

	public JerseyOutboundAuthFilter(final CredentialClient credentialClient) {
		this.credentialClient = credentialClient;
	}

	public void setActorResolver(final ActorResolver actorResolver) {
		this.actorResolver = actorResolver;
	}

	@Override
	public ClientResponse handle(final ClientRequest cr) throws ClientHandlerException {
		cr.getHeaders().add("Authorization", getHeaderString());
		ClientResponse resp = getNext().handle(cr);
		return resp;
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
