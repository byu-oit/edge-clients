package edu.byu.security.hmac.jersey;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import edu.byu.edge.client.pro.domain.personSummary.JaxbContextResolver;
import org.apache.log4j.Logger;

/**
 * Author: Wyatt Taylor (wyatt_taylor@byu.edu)
 * Date: 5/24/12
 */
public abstract class BaseClient {

	private static final Logger LOG = Logger.getLogger(BaseClient.class);

	protected final String baseUrl;
	protected final BaseHeaderAuthenticationClientFilter filter;
	protected final Client client;
	protected final WebResource webResource;

	protected BaseClient(final String baseUrl, final BaseHeaderAuthenticationClientFilter filter, final int readTimeout) {
		this.baseUrl = baseUrl;
		this.filter = filter;
		final ClientConfig config = new DefaultClientConfig();
		config.getClasses().add(JaxbContextResolver.class);
		this.client = initClient(config);
		this.client.addFilter(filter);
		this.client.setReadTimeout(readTimeout);
		this.webResource = this.client.resource(baseUrl);
	}

	protected Client initClient(ClientConfig config) {
		return Client.create(config);
	}

	protected final Client getClient() {
		return client;
	}

	protected WebResource getResource() {
		return webResource;
	}
}
