package edu.byu.auth.client.impl;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import edu.byu.auth.client.CredentialClient;
import edu.byu.auth.domain.AuthJaxbContextResolver;
import edu.byu.auth.domain.Nonce;
import edu.byu.hash.Base64;
import org.springframework.beans.factory.InitializingBean;

import javax.crypto.spec.SecretKeySpec;
import javax.ws.rs.core.MediaType;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * Author: Wyatt Taylor (wyatt_taylor@byu.edu)
 * Date: 08/29/2014
 *
 * @author Wyatt Taylor (wyatt_taylor@byu.edu)
 * @since 08/29/2014
 */
public abstract class CredentialClientImpl implements CredentialClient, InitializingBean {

	protected static final String WS_BASE_URL = "https://ws.byu.edu/authentication/services/rest/v1";
	protected static final String NONCE_PATH = "/hmac/nonce";
	protected static final MediaType[] ACCEPT_MEDIA_TYPES = {MediaType.APPLICATION_XML_TYPE, MediaType.TEXT_XML_TYPE, MediaType.APPLICATION_JSON_TYPE};

	protected static final MediaType AF_URLENC_TYPE = MediaType.APPLICATION_FORM_URLENCODED_TYPE;

	protected Client client;
//	protected ClientFilter[] filters;
	protected WebResource baseRes;
	protected WebResource nonceRes;

	/**
	 * @param connectTimeout the time to allow for connections in milliseconds
	 * @param readTimeout the time to wait for response in milliseconds
	 */
	protected CredentialClientImpl(final int connectTimeout, final int readTimeout) {
		final DefaultClientConfig config = new DefaultClientConfig();
		config.getClasses().add(AuthJaxbContextResolver.class);
		this.client = initClient(config);
		this.client.setConnectTimeout(connectTimeout);
		this.client.setReadTimeout(readTimeout);
	}

//	public void setFilters(final ClientFilter... filters) {
//		this.filters = filters;
//	}

	protected Client initClient(final ClientConfig config) {
		return Client.create(config);
	}

	@Override
	public final void afterPropertiesSet() throws Exception {
		preClientConfig();
//		if (filters != null && filters.length > 0) {
//			for (final ClientFilter f : filters) {
//				this.client.addFilter(f);
//			}
//		}
		this.baseRes = this.client.resource(WS_BASE_URL);
		this.nonceRes = this.baseRes.path(NONCE_PATH);
		postClientConfig();
	}

	protected abstract void preClientConfig();
	protected abstract void postClientConfig();
	protected abstract String getHeaderType();

	protected String calcHmac(final Nonce nonce) {
		try {
			final javax.crypto.SecretKey key = new SecretKeySpec(nonce.getSharedSecret().getBytes("UTF-8"), "HmacSHA512");
			final javax.crypto.Mac signer = javax.crypto.Mac.getInstance(key.getAlgorithm());
			signer.init(key);
			final byte[] hashedMessageBytes = signer.doFinal(nonce.getNonceValue().getBytes("UTF-8"));
			return Base64.encode(hashedMessageBytes, false);
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException("Unable to compute HMAC - algorithm.", e);
		} catch (InvalidKeyException e) {
			throw new IllegalStateException("Unable to compute HMAC - invalid key.", e);
		} catch (UnsupportedEncodingException e) {
			throw new IllegalStateException("Unable to compute HMAC - encoding.", e);
		}
	}

	protected String _doObtainAuthorizationString(final Nonce nonce) {
		return getHeaderType() + " " + nonce.getApiKey() + "," + nonce.getNonceKey() + "," + calcHmac(nonce);
	}

	@Override
	public String obtainAuthorizationHeaderString() {
		return _doObtainAuthorizationString(obtainNonce());
	}

	@Override
	public String obtainAuthorizationHeaderString(final String actor) {
		return _doObtainAuthorizationString(obtainNonce(actor));
	}

	@Override
	public String obtainAuthorizationHeaderString(final String actor, final String idType) {
		return _doObtainAuthorizationString(obtainNonce(actor, idType));
	}

	@Override
	public Nonce obtainNonce(final String actorId, final String idType) {
		return obtainNonce(actorId + "/" + idType);
	}
}
