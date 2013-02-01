package edu.byu.security.webSession.ws.authentication;

/**
 * Created by IntelliJ IDEA.
 * User: thirschi
 * Date: 1/17/13
 * Time: 10:40 AM
 */
public interface WebSessionLookup {

	WebSessionData getWebSessionDataBySessionKey(final String sessionKey);
}
