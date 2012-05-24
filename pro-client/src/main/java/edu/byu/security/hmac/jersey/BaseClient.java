package edu.byu.security.hmac.jersey;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import org.apache.log4j.Logger;

/**
 * Author: Wyatt Taylor (wyatt_taylor@byu.edu)
 * Date: 5/24/12
 */
public abstract class BaseClient {

	private static final Logger LOG = Logger.getLogger(BaseClient.class);

	protected final Client client;

	protected BaseClient(final BaseHeaderAuthenticationClientFilter filter, final int readTimeout) {
		final ClientConfig config = new DefaultClientConfig();
		final BodyRenderingClientHandler clientHandler = new BodyRenderingClientHandler();
		config.getSingletons().add(clientHandler);
		client = initClient(config);
		client.addFilter(filter);
		client.setReadTimeout(readTimeout);
	}

	protected Client initClient(ClientConfig config) {
		return Client.create(config);
	}

	protected final Client getClient() {
		return client;
	}
}
