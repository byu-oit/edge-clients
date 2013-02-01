package edu.byu.security.webSession.ws.authentication;

import edu.byu.security.authentication.WebSessionAuthenticationRequest;
import edu.byu.security.ws.authentication.rest.HttpServletRequestCredentialExtractor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by IntelliJ IDEA.
 * User: thirschi
 * Date: 1/16/13
 * Time: 2:53 PM
 */
public class WebSessionExtractor implements HttpServletRequestCredentialExtractor<WebSessionAuthenticationRequest> {

	private final WebSessionLookup webSessionLookup;

	public WebSessionExtractor(WebSessionLookup webSessionLookup) {
		this.webSessionLookup = webSessionLookup;
	}

	@Override
	public WebSessionAuthenticationRequest extractAuthenticationRequest(final HttpServletRequest req) {
		final Cookie cookie = getWebsessionCookie(req);
		if(cookie == null){
			return null;
		}
		final WebSessionData data = webSessionLookup.getWebSessionDataBySessionKey(cookie.getValue());
		return new WebSessionAuthenticationRequest(new WebSession(data));
	}

	@Override
	public boolean canExtractCredential(final HttpServletRequest httpServletRequest) {
		return getWebsessionCookie(httpServletRequest) != null;
	}

	@Override
	public boolean consumesRequestBody() {
		return false;
	}

	@Override
	public BodyType consumesBodyAs(final HttpServletRequest request) {
		return BodyType.NONE;
	}

	private Cookie getWebsessionCookie(final HttpServletRequest request){
		final Cookie[] cookies = request.getCookies();
		for(final Cookie c : cookies){
			if(c.getName().equals("BYU-Web-Session")){
				return c;
			}
		}
		return null;
	}
}
