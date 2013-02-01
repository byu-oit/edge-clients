package edu.byu.security.authentication;

import edu.byu.security.webSession.ws.authentication.WebSession;
import org.springframework.security.core.Authentication;
import org.springframework.util.Assert;

/**
 * Created by IntelliJ IDEA.
 * User: thirschi
 * Date: 1/16/13
 * Time: 2:34 PM
 */
public class WebSessionAuthenticationRequest extends CredentialBasedAuthenticationRequest<WebSession> implements Authentication {

	private final WebSession webSession;

	public WebSessionAuthenticationRequest(final WebSession webSession){
		super(webSession);
		Assert.notNull(webSession, "A credential is required to create an authentication request.");
		this.webSession = webSession;
	}

	@Override
	public Object getDetails() {
		return webSession.getId();
	}

	@Override
	public String getPrincipal() {
		return webSession.getPersonId();
	}

	@Override
	public String getName() {
		return webSession.getPersonId();
	}
}
