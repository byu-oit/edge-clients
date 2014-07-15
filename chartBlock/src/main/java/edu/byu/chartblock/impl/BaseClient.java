package edu.byu.chartblock.impl;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.ClientFilter;
import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: thirschi
 * Date: 7/2/2014
 * Time: 3:05 PM
 */
public abstract class BaseClient {
	private static final Logger LOG = Logger.getLogger(BaseClient.class);

	protected final String baseUrl;
	protected final ClientFilter filter;
	protected final Client client;
	protected final WebResource webResource;

	/**
	 *
	 * @param baseUrl the base url of the service
	 * @param filter the nonce encoding filter
	 * @param readTimeout the default read timeout for the service
	 */
	protected BaseClient(final String baseUrl, final ClientFilter filter, final int readTimeout) {
		this.baseUrl = baseUrl;
		this.filter = filter;
		final ClientConfig config = new DefaultClientConfig();
		this.client = initClient(config);
		this.client.addFilter(filter);
		this.client.setReadTimeout(readTimeout);
		this.client.setFollowRedirects(true);
		this.webResource = this.client.resource(baseUrl);
	}

	protected void setSoapAction(String soapAction){
		this.webResource.header("SOAPAction", soapAction);
	}

	/**
	 * Configures a client with the provided configuration. Override to add custom configuration.
	 * @param config the configuration
	 * @return the client
	 */
	protected Client initClient(ClientConfig config) {
		try {
			return Client.create(config);
		} catch (final Throwable t) {
			LOG.error("error initializing client", t);
			if (RuntimeException.class.isAssignableFrom(t.getClass())) throw (RuntimeException) t;
			throw new RuntimeException(t);
		}
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

	/**
	 * Processes the given throwable to convert it to the BYU common exceptions.
	 * Determines if the throwable was caused by something that we should retry.
	 * @param t the throwable
	 * @return true if the throwable appears to be 'retryable', false otherwise
	 */
	protected boolean processExceptionForRetry(final Throwable t) {
		if (t == null) return false;
		if (UniformInterfaceException.class.isAssignableFrom(t.getClass())) {
			if (t.getMessage().contains(" returned a response status of 502 Bad Gateway")) {
				return true;
			}
		}
		return false;
	}
}
