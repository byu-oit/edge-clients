package edu.byu.edge.client.controldates;

import com.sun.jersey.api.client.filter.ClientFilter;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

import edu.byu.commons.exception.NotAuthorizedException;
import edu.byu.commons.exception.NotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class BaseClient {

	private static final Logger LOG = LogManager.getLogger(BaseClient.class);

	protected final String baseUrl;
	protected final Client client;
	protected final WebResource webResource;

	protected BaseClient(final String baseUrl, final int readTimeout){
		this(baseUrl, readTimeout, null);
	}

	protected BaseClient(final String baseUrl, final int readTimeout, final ClientFilter ... filters) {
		this.baseUrl = baseUrl;
		final ClientConfig config = new DefaultClientConfig();
		this.client = initClient(config);
		if (filters != null){
			for (ClientFilter filter : filters) {
				this.client.addFilter(filter);
			}
		}
		this.client.setReadTimeout(readTimeout);
		this.webResource = this.client.resource(baseUrl);
	}

	/**
	 * Configures a client with the provided configuration. Override to add
	 * custom configuration.
	 * 
	 * @param config
	 *            the configuration
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

	/**
	 * Processes an exception to either convert it to a BYU Exception, or do
	 * nothing. Callers of this method should
	 * 
	 * @param t
	 *            The exception to process.
	 * @return
	 */
	protected Throwable processExceptionToCommon(final Throwable t) {
		if (t == null)
			return null;
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
	 * 
	 * @param t
	 *            the throwable
	 * @return true if the throwable appears to be 'retryable', false otherwise
	 */
	protected boolean processExceptionForRetry(final Throwable t) {
		if (t == null)
			return false;
		if (UniformInterfaceException.class.isAssignableFrom(t.getClass())) {
			if (t.getMessage()
					.contains(" returned a response status of 502 Bad Gateway")) {
				return true;
			}
		}
		return false;
	}

}
