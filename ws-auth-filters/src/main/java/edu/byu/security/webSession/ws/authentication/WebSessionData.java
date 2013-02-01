package edu.byu.security.webSession.ws.authentication;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: thirschi
 * Date: 1/17/13
 * Time: 10:23 AM
 */
public class WebSessionData {

	private String sessionKey;
	private String userName;
	private Date inactiveExpireDateTime;
	private Date expireDateTime;

	public WebSessionData() {
	}

	public WebSessionData(String sessionKey, String userName, Date inactiveExpireDateTime, Date expireDateTime) {
		this.sessionKey = sessionKey;
		this.userName = userName;
		this.inactiveExpireDateTime = inactiveExpireDateTime;
		this.expireDateTime = expireDateTime;
	}

	public String getSessionKey() {
		return sessionKey;
	}

	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getInactiveExpireDateTime() {
		return inactiveExpireDateTime;
	}

	public void setInactiveExpireDateTime(Date inactiveExpireDateTime) {
		this.inactiveExpireDateTime = inactiveExpireDateTime;
	}

	public Date getExpireDateTime() {
		return expireDateTime;
	}

	public void setExpireDateTime(Date expireDateTime) {
		this.expireDateTime = expireDateTime;
	}
}
