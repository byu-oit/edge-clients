package edu.byu.edge.client.pro.impl;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import edu.byu.edge.client.pro.domain.personSummary.JaxbContextResolver;
import edu.byu.security.hmac.jersey.SharedSecretNonceEncodingFilter;
import org.apache.log4j.Logger;

/**
 * Base class to handle client configuration.
 *
 * Author: Wyatt Taylor (wyatt_taylor@byu.edu)
 * Date: 5/24/12
 */
public abstract class BaseClient {

	private static final Logger LOG = Logger.getLogger(BaseClient.class);

	protected final String baseUrl;
	protected final SharedSecretNonceEncodingFilter filter;
	protected final Client client;
	protected final WebResource webResource;

	/**
	 *
	 * @param baseUrl the base url of the service
	 * @param filter the nonce encoding filter
	 * @param readTimeout the default read timeout for the service
	 */
	protected BaseClient(final String baseUrl, final SharedSecretNonceEncodingFilter filter, final int readTimeout) {
		this.baseUrl = baseUrl;
		this.filter = filter;
		final ClientConfig config = new DefaultClientConfig();
//		config.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
		config.getClasses().add(JaxbContextResolver.class);
		this.client = initClient(config);
		this.client.addFilter(filter);
		this.client.setReadTimeout(readTimeout);
		this.webResource = this.client.resource(baseUrl);
	}

	/**
	 * Configures a client with the provided configuration. Override to add custom configuration.
	 * @param config the configuration
	 * @return the client
	 */
	protected Client initClient(ClientConfig config) {
		return Client.create(config);
	}

	/**
	 * @return the client
	 */
	protected final Client getClient() {
		return client;
	}

	/**
	 * @return the web resource
	 */
	protected WebResource getResource() {
		return webResource;
	}
}

