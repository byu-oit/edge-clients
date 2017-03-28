package edu.byu.edge.client.pro.impl;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.filter.ClientFilter;
import com.sun.jersey.client.apache4.config.ApacheHttpClient4Config;
import com.sun.jersey.client.apache4.config.DefaultApacheHttpClient4Config;
import edu.byu.commons.exception.NotAuthorizedException;
import edu.byu.commons.exception.NotFoundException;
import edu.byu.edge.client.pro.domain.personSummary.JaxbContextResolver;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Base class to handle client configuration.
 *
 * Author: Wyatt Taylor (wyatt_taylor@byu.edu)
 * Date: 5/24/12
 */

public abstract class BaseClient {

	private static final Logger LOG = Logger.getLogger(BaseClient.class);

	protected final String baseUrl;
	protected final Client client;
	protected final WebResource webResource;

	/**
	 *  @param baseUrl the base url of the service
	 * @param readTimeout the default read timeout for the service
	 * @param filters the nonce encoding filter
	 */
	protected BaseClient(final String baseUrl, final int readTimeout, final List<ClientFilter> filters) {
		this(baseUrl, filters, readTimeout, 5000);
	}

	/**
	 *
	 * @param baseUrl the base url of the service
	 * @param filters the nonce encoding filter
	 * @param readTimeout the default read timeout for the service
	 * @param connectTimeout connect timeout
	 */
	protected BaseClient(final String baseUrl, final List<ClientFilter> filters, final int readTimeout, final int connectTimeout) {
		this.baseUrl = baseUrl;
//		final ClientConfig config = new DefaultClientConfig();
		//		config.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
		final DefaultApacheHttpClient4Config config = new DefaultApacheHttpClient4Config();
		config.getProperties().put(ApacheHttpClient4Config.PROPERTY_PROXY_URI, "http://east.byu.edu:3128");
		config.getClasses().add(JaxbContextResolver.class);
		this.client = initClient(config);
		for (ClientFilter filter : filters) {
			this.client.addFilter(filter);
		}
		this.client.setReadTimeout(readTimeout);
		this.client.setConnectTimeout(connectTimeout);
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
	 * @return result
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

	private static String nss(final String s) {
		return s == null ? "" : s;
	}

	private static String getPropValue(final String envKey) {
		if (System.getenv().containsKey(envKey)) {
			return nss(System.getenv(envKey));
		}
		if (System.getProperties().containsKey(envKey)) {
			return nss(System.getProperties().getProperty(envKey));
		}
		return "";
	}

}

