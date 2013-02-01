package edu.byu.security.webSession.ws.authentication;

import edu.byu.security.Credential;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: thirschi
 * Date: 1/16/13
 * Time: 2:36 PM
 */
public class WebSession implements Credential, Serializable {

	private static final long serialVersionUID = 1L;
	public final WebSessionData webSession;

	public WebSession(WebSessionData webSessionData) {
		this.webSession = webSessionData;
	}

	@Override
	public String getPersonId() {
		return webSession.getUserName();
	}

	@Override
	public String getId() {
		return webSession.getUserName();
	}

	@Override
	public String getValue() {
		return null;
	}

	@Override
	public boolean isExpired() {
		return webSession.getExpireDateTime().after(new Date());
	}

	@Override
	public void expire() {
	}

	@Override
	public Date getExpirationDate() {
		return webSession.getExpireDateTime();
	}

	@Override
	public boolean isDisabled() {
		return webSession.getInactiveExpireDateTime().after(new Date());
	}

	@Override
	public void disable() {
	}
}
