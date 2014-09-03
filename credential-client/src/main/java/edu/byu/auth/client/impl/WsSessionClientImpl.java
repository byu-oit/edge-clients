package edu.byu.auth.client.impl;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import edu.byu.auth.client.PasswordCredentialResolver;
import edu.byu.auth.client.WsSessionClient;
import edu.byu.auth.domain.Nonce;
import edu.byu.auth.domain.WsSession;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Author: Wyatt Taylor (wyatt_taylor@byu.edu)
 * Date: 08/29/2014
 *
 * @author Wyatt Taylor (wyatt_taylor@byu.edu)
 * @since 08/29/2014
 */
public class WsSessionClientImpl extends CredentialClientImpl implements WsSessionClient, InitializingBean {

	public static final String HEADER_TYPE = "";

	private static final AtomicInteger COUNTER = new AtomicInteger(0);

	private static final int RENEW_BUFFER = 5000;

	protected static final String HDR_NAME = "Authorization";

	private PasswordCredentialResolver resolver;
	private int sessionDuration;
	private boolean autoRenew = false;
	private WebResource wsBaseRes;
	private Thread renewer;
	private final AtomicReference<WsSession> session = new AtomicReference<WsSession>();
	private final AtomicBoolean isRenewing = new AtomicBoolean(false);
	private final Object lockObj = new Object();

	public WsSessionClientImpl(final int connectTimeout, final int readTimeout) {
		super(connectTimeout, readTimeout);
		this.wsBaseRes = baseRes.path("ws/session");
	}

	public void setSessionDuration(final int sessionDuration) {
		this.sessionDuration = sessionDuration;
	}

	public void setResolver(final PasswordCredentialResolver resolver) {
		this.resolver = resolver;
	}

	public void setAutoRenew(final boolean autoRenew) {
		this.autoRenew = autoRenew;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(resolver, "A valid PasswordCredentialResolver is required");
		if (sessionDuration < 1) sessionDuration = 1;
		if (sessionDuration > 480) sessionDuration = 480;
		Assert.notNull(resolver.getUsername(), "Resolver failed to return a valid username.");
		Assert.notNull(resolver.getPassword(), "Resolver failed to return a password.");
		login();
		if (autoRenew) {
			renewer = new RenewThread(this);
			renewer.start();
		}
	}

	@Override
	protected String getHeaderType() {
		return HEADER_TYPE;
	}

	@Override
	public Nonce obtainNonce() {
		final WsSession ws = getSession();
		try {
			return _doObtainNonce(ws);
		} catch (final UniformInterfaceException e) {
			if (e.getResponse().getStatus() == ClientResponse.Status.BAD_GATEWAY.getStatusCode()) {
				return _doObtainNonce(ws);
			} else {
				throw e;
			}
		}
	}

	@Override
	public void logout() {
		try {
			final Nonce nonce = obtainNonce();
			baseRes.path(nonce.getApiKey()).header(HDR_NAME, super._doObtainAuthorizationString(nonce)).delete();
		} catch (final Throwable ignore) {
		}
	}

	@Override
	public void login() {
		final MultivaluedMapImpl map = new MultivaluedMapImpl();
		map.putSingle("netId", resolver.getUsername());
		map.putSingle("password", resolver.getPassword());
		map.putSingle("timeout", sessionDuration);
		this.session.set(wsBaseRes.accept(ACCEPT_MEDIA_TYPES).entity(map, AF_URLENC_TYPE).post(WsSession.class));
	}

	@Override
	public void renew() {
		if (isRenewing.get()) return;
		try {
			isRenewing.set(true);
			final Nonce nonce = _doObtainNonce(session.get());
			final String hdrVal = super._doObtainAuthorizationString(nonce);
			final Map<String, Object> params = new HashMap<String, Object>();
			params.put("timeout", sessionDuration);
			final WsSession newws = baseRes.path(nonce.getApiKey()).header(HDR_NAME, hdrVal).entity(params, AF_URLENC_TYPE).accept(ACCEPT_MEDIA_TYPES)
					.post(WsSession.class);
			session.set(newws);
		} finally {
			isRenewing.set(false);
		}
	}

	private Nonce _doObtainNonce(final WsSession ws) {
		final Nonce n = baseRes.path(ws.getApiKey()).accept(ACCEPT_MEDIA_TYPES).post(Nonce.class);
		n.setApiKey(ws.getApiKey());
		n.setSharedSecret(ws.getSharedSecret());
		return n;
	}

	private WsSession getSession() {
		while (isRenewing.get()) {
			try {
				Thread.sleep(0, 1);
			} catch (InterruptedException ignore) {
			}
		}
		return session.get();
	}

	private static class RenewThread extends Thread {
		private final WsSessionClientImpl client;
		private AtomicBoolean keepRunning = new AtomicBoolean(true);

		private RenewThread(final WsSessionClientImpl client) {
			super("WsSessionRenew-" + COUNTER.getAndIncrement());
			this.client = client;
			this.setDaemon(true);
		}

		@Override
		public void run() {
			while (keepRunning.get()) {
				try {
					Thread.sleep(client.session.get().getExpireDate().getTimeInMillis() - System.currentTimeMillis() - RENEW_BUFFER - 500);
				} catch (final Throwable ignore) {
				}
				try {
					client.renew();
				} catch (final Throwable t) {
					try {
						client.renew();
					} catch (final Throwable u) {
						//crap
					}
				}
			}
		}
	}
}
