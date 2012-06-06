package edu.byu.edge.client.auth.impl;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import edu.byu.edge.client.auth.ByuAuthClient;
import edu.byu.edge.client.domain.AuthJaxbContextResolver;
import edu.byu.edge.client.domain.Nonce;
import edu.byu.edge.client.domain.WsSession;
import org.apache.log4j.Logger;

import javax.ws.rs.core.MediaType;
import java.util.*;

/**
 * Author: Wyatt Taylor (wyatt_taylor@byu.edu)
 * Date: 6/6/12
 */
public class ByuAuthClientImpl implements ByuAuthClient {

	private static final Logger LOG = Logger.getLogger(ByuAuthClientImpl.class);

	/** */
	protected static final int DEFAULT_CONNECTION_TIMEOUT = 30000;

	/** */
	protected static final int DEFAULT_SESSION_TIMEOUT = 15;

	/** */
	protected static final String WS_BASE_URL = "https://ws.byu.edu/authentication";

	/** */
	protected static final String LOGIN_PATH = "/services/rest/v1/ws/session";

	/** */
	protected static final String NONCE_PATH = "/services/rest/v1/hmac/nonce";

	/** */
	protected static final MediaType XML_TYP = MediaType.APPLICATION_XML_TYPE;

	/** */
	protected static final MediaType JSON_TYP = MediaType.APPLICATION_JSON_TYPE;

	/** */
	protected static final MediaType URL_FORM_TYP = MediaType.APPLICATION_FORM_URLENCODED_TYPE;

	protected final Client client;
	protected final WebResource webResource;

	/**
	 * Initializes the client with default timeout.
	 */
	public ByuAuthClientImpl() {
		this(DEFAULT_CONNECTION_TIMEOUT);
	}

	/**
	 * Initializes the client with the specified timeout.
	 * @param readTimeout the default read timeout for the service, in milliseconds
	 */
	public ByuAuthClientImpl(final int readTimeout) {
		final ClientConfig config = new DefaultClientConfig();
		config.getClasses().add(AuthJaxbContextResolver.class);
		this.client = initClient(config);
		this.client.setReadTimeout(readTimeout);
		this.webResource = this.client.resource(WS_BASE_URL);
	}

	/**
	 * Configures a client with the provided configuration. Override to add custom configuration.
	 * @param config the configuration
	 * @return the client
	 */
	protected Client initClient(ClientConfig config) {
		return Client.create(config);
	}

	@Override
	public WsSession login(final String netId, final String password) {
		return login(netId, password, DEFAULT_SESSION_TIMEOUT);
	}

	@Override
	public WsSession login(final String netId, final String password, final int timeout) {
		final MultivaluedMapImpl map = new MultivaluedMapImpl();
		map.put("netId", Arrays.asList(netId));
		map.put("password", Arrays.asList(password));
		map.put("timeout", Arrays.asList(String.valueOf(timeout)));
		final StringBuilder sb = new StringBuilder();
		sb.append("netId=");
		sb.append(netId);
		sb.append("password=");
		sb.append(password);
		sb.append("timeout=");
		sb.append(timeout);
		return webResource.path(LOGIN_PATH).accept(XML_TYP).entity(map, URL_FORM_TYP).post(WsSession.class);
	}

	@Override
	public WsSession renew(final WsSession session) {
		return renew(session, DEFAULT_SESSION_TIMEOUT);
	}

	@Override
	public WsSession renew(final WsSession session, final int timeout) {
		return webResource.path(LOGIN_PATH + "/" + session.getApiKey()).accept(XML_TYP).entity("timeout=" + timeout, URL_FORM_TYP).post(WsSession.class);
	}

	@Override
	public void logout(final WsSession session) {

	}

	@Override
	public Nonce obtainNonce(final WsSession session) {
		return null;
	}

	@Override
	public Nonce obtainNonce(final WsSession session, final String actorNetId) {
		return null;
	}

}
