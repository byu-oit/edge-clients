package edu.byu.edge.client.auth.impl;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import edu.byu.edge.client.auth.ByuAuthClient;
import edu.byu.edge.client.auth.ExpiredSessionException;
import edu.byu.edge.client.domain.AuthJaxbContextResolver;
import edu.byu.edge.client.domain.Nonce;
import edu.byu.edge.client.domain.WsSession;
import edu.byu.hash.Base64;
import org.apache.log4j.Logger;

import javax.crypto.spec.SecretKeySpec;
import javax.ws.rs.core.MediaType;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * Author: Wyatt Taylor (wyatt_taylor@byu.edu)
 * Date: 6/6/12
 */
public class ByuAuthClientImpl implements ByuAuthClient {

	private static final Logger LOG = Logger.getLogger(ByuAuthClientImpl.class);

	/** */
	public static final String AUTH_HEADER_NAME = "authorization";

	/**
	 * Constant indicating the minimum amount of time prior to session expiration that we'll allow a logout.
	 * In other words, if the session expires within this timeframe, it is considered 'imminent' and we'll just let it expire.
	 */
	public static final int DEFAULT_EXPIRATION_BUFFER = 1000;

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
	protected final int expirationBuffer;

	/**
	 * Initializes the client with default timeout and default expiration buffer.
	 */
	public ByuAuthClientImpl() {
		this(DEFAULT_CONNECTION_TIMEOUT);
	}

	/**
	 * Initializes the client with the specified timeout and the default expiration buffer.
	 * @param readTimeout the read timeout for the service, in milliseconds
	 */
	public ByuAuthClientImpl(final int readTimeout) {
		this(DEFAULT_CONNECTION_TIMEOUT, DEFAULT_EXPIRATION_BUFFER);
	}

	/**
	 * Initializes the client with the specified timeout and the specified expiration buffer.
	 * @param readTimeout the read timeout for the service, in milliseconds
	 * @param expirationBuffer the expiration timeframe for the service, in milliseconds
	 */
	public ByuAuthClientImpl(final int readTimeout, final int expirationBuffer) {
		final ClientConfig config = new DefaultClientConfig();
		config.getClasses().add(AuthJaxbContextResolver.class);
		this.client = initClient(config);
		this.client.setReadTimeout(readTimeout);
		this.webResource = this.client.resource(WS_BASE_URL);
		this.expirationBuffer = expirationBuffer;
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
		return webResource.path(LOGIN_PATH).accept(XML_TYP).entity(map, URL_FORM_TYP).post(WsSession.class);
	}

	@Override
	public WsSession renew(final WsSession session) {
		return renew(session, DEFAULT_SESSION_TIMEOUT);
	}

	@Override
	public WsSession renew(final WsSession session, final int timeout) {
		final String path = LOGIN_PATH + "/" + session.getApiKey();
		final Nonce nonce = obtainNonce(session);
		return webResource
					   .path(path)
					   .accept(XML_TYP)
					   .entity("timeout=" + timeout, URL_FORM_TYP)
					   .header(AUTH_HEADER_NAME, getHeaderValue(session, nonce))
					   .post(WsSession.class);
	}

	@Override
	public void logout(final WsSession session) {
		final long now = System.currentTimeMillis();
		if (session.getExpireDate().getTimeInMillis() >= now - expirationBuffer) {
			LOG.info("attempting logout of expiring/expired session");
			return;
		}
		final String path = LOGIN_PATH + "/" + session.getApiKey();
		try {
			final Nonce nonce = obtainNonce(session);
			webResource.path(path).header(AUTH_HEADER_NAME, getHeaderValue(session, nonce)).delete();
		} catch (final ExpiredSessionException e) {
			//no op, the user is logging out which accomplishes the same thing as an expired session
		}
	}

	@Override
	public Nonce obtainNonce(final WsSession session) {
		final String path = NONCE_PATH + "/" + session.getApiKey();
		return webResource.path(path).accept(XML_TYP).post(Nonce.class);
	}

	@Override
	public Nonce obtainNonce(final WsSession session, final String actorNetId) {
		final String path = NONCE_PATH + "/" + session.getApiKey() + "/" + actorNetId;
		return webResource.path(path).accept(XML_TYP).post(Nonce.class);
	}

	@Override
	public String calculateHmac(final WsSession session, final Nonce nonce)  {
		final String ss = session.getSharedSecret();
		final String val = nonce.getNonceValue();
		try {
			javax.crypto.SecretKey key = new SecretKeySpec(ss.getBytes("UTF-8"), "HmacSHA512");
			javax.crypto.Mac signer = javax.crypto.Mac.getInstance(key.getAlgorithm());
			signer.init(key);
			byte[] hashedMessageBytes = signer.doFinal(val.getBytes("UTF-8"));
			return Base64.encode(hashedMessageBytes, false);
		} catch (final UnsupportedEncodingException e) {
			LOG.error("error computing HMAC", e);
			throw new IllegalStateException("Unable to compute HMAC", e);
		} catch (final NoSuchAlgorithmException e) {
			LOG.error("error computing HMAC", e);
			throw new IllegalStateException("Unable to compute HMAC", e);
		} catch (final InvalidKeyException e) {
			LOG.error("error computing HMAC", e);
			throw new IllegalStateException("Unable to compute HMAC", e);
		}
	}

	@Override
	public String getHeaderValue(final WsSession session, final Nonce nonce) {
		return getHeaderValue(session, nonce, calculateHmac(session, nonce));
	}

	@Override
	public String getHeaderValue(final WsSession session, final Nonce nonce, final String hmac) {
		return "Nonce-Encoded-WsSession-Key " + session.getApiKey() + "," + nonce.getNonceKey() + "," + hmac;
	}
}
