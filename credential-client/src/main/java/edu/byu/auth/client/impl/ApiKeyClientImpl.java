package edu.byu.auth.client.impl;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import edu.byu.auth.client.ApiKeyClient;
import edu.byu.auth.client.SharedSecretCredentialResolver;
import edu.byu.auth.domain.Credential;
import edu.byu.auth.domain.Nonce;
import org.springframework.util.Assert;

import java.util.GregorianCalendar;

/**
 * Author: Wyatt Taylor (wyatt_taylor@byu.edu)
 * Date: 08/29/2014
 *
 * @author Wyatt Taylor (wyatt_taylor@byu.edu)
 * @since 08/29/2014
 */
public class ApiKeyClientImpl extends CredentialClientImpl implements ApiKeyClient {

	public static final String HEADER_TYPE = "Nonce-Encoded-API-Key";

	private SharedSecretCredentialResolver resolver;
	private Credential apikey;
	private WebResource apikeyRes;

	/**
	 * @param connectTimeout the time to allow for connections in milliseconds
	 * @param readTimeout the time to wait for response in milliseconds
	 */
	public ApiKeyClientImpl(final int connectTimeout, final int readTimeout) {
		super(connectTimeout, readTimeout);
	}

	public void setResolver(final SharedSecretCredentialResolver resolver) {
		this.resolver = resolver;
	}


	@Override
	protected void preClientConfig() {
		Assert.notNull(resolver, "A valid SharedSecretCredentialResolver is required.");
		apikey = resolver.getApiKeyCredential();
		Assert.notNull(apikey, "The resolver did not resolve any credentials.");
		Assert.isTrue(apikey.getExpirationDate().after(new GregorianCalendar()), "The resolved credential is already expired.");
	}

	@Override
	protected void postClientConfig() {
		apikeyRes = super.nonceRes.path(apikey.getWsId());
	}

	@Override
	protected String getHeaderType() {
		return HEADER_TYPE;
	}

	@Override
	public Nonce obtainNonce() {
		try {
			return _doObtainNonce();
		} catch (final UniformInterfaceException e) {
			if (e.getResponse().getStatus() == ClientResponse.Status.BAD_GATEWAY.getStatusCode()) {
				return _doObtainNonce();
			} else {
				throw e;
			}
		}
	}

	@Override
	public Nonce obtainNonceWithActor(final String actorId) {
		try {
			return _doObtainNonceWithActor(actorId);
		} catch (final UniformInterfaceException e) {
			if (e.getResponse().getStatus() == ClientResponse.Status.BAD_GATEWAY.getStatusCode()) {
				return _doObtainNonceWithActor(actorId);
			} else {
				throw e;
			}
		}
	}

	@Override
	public Nonce obtainNonceWithActor(final String actorId, final String idType) {
		try {
			return _doObtainNonceWithActor(actorId + "/" + idType);
		} catch (final UniformInterfaceException e) {
			if (e.getResponse().getStatus() == ClientResponse.Status.BAD_GATEWAY.getStatusCode()) {
				return _doObtainNonceWithActor(actorId + "/" + idType);
			} else {
				throw e;
			}
		}
	}

	private Nonce _doObtainNonce() {
		final Nonce n = apikeyRes.accept(ACCEPT_MEDIA_TYPES).post(Nonce.class);
		n.setApiKey(apikey.getWsId());
		n.setSharedSecret(apikey.getSharedSecret());
		return n;
	}

	private Nonce _doObtainNonceWithActor(final String actorId) {
		final Nonce n = apikeyRes.path(actorId).accept(ACCEPT_MEDIA_TYPES).post(Nonce.class);
		n.setApiKey(apikey.getWsId());
		n.setSharedSecret(apikey.getSharedSecret());
		return n;
	}
}
