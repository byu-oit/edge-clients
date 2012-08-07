package edu.byu.edge.client.pro.impl;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import edu.byu.commons.exception.NotAuthorizedException;
import edu.byu.commons.exception.NotFoundException;
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
	 * Processes an exception to either convert it to a BYU Exception, or do nothing.
	 * Callers of this method should
	 * @param t The exception to process.
	 * @return
	 */
	protected Throwable processExceptionToCommon(final Throwable t) {
		if (t == null) return null;
		if (UniformInterfaceException.class.isAssignableFrom(t.getClass())) {
			final String message = t.getMessage();
			if (message.contains(" returned a response status of 403 Forbidden")) {
				return new NotAuthorizedException(t);
			} else if (message.contains(" returned a response status of 404 Not Found")) {
				return new NotFoundException(t);
			}
		}
		return t;
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

