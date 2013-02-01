package edu.byu.security.webSession.ws.authentication;

import edu.byu.security.authentication.IdentityBasedAuthenticationResult;
import edu.byu.security.authentication.WebSessionAuthenticationRequest;
import edu.byu.security.userdetails.IdentityDetails;
import edu.byu.security.userdetails.IdentityDetailsService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.util.Assert;

/**
 * Created by IntelliJ IDEA.
 * User: thirschi
 * Date: 1/16/13
 * Time: 3:04 PM
 */
public class WebSessionProvider implements AuthenticationProvider, InitializingBean {

	private static final Logger LOG = Logger.getLogger(WebSessionProvider.class);

	private IdentityDetailsService idService;

	public void setIdService(IdentityDetailsService idService) {
		this.idService = idService;
	}

	@Override
	public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
		if (authentication == null || !supports(authentication.getClass())) return null;
		final WebSessionAuthenticationRequest req = (WebSessionAuthenticationRequest) authentication;
		try {
			final IdentityDetails id = (IdentityDetails) idService.loadUserByUsername(req.getPrincipal());
			return new IdentityBasedAuthenticationResult(id, req, true);
		} catch (final DataAccessException e) {
			throw new AuthenticationServiceException("an error occurred while retrieving the identity.", e);
		} catch (final Exception e) {
			throw new AuthenticationServiceException("An unexpected error has occurred.", e);
		}
	}

	@Override
	public boolean supports(final Class<?> authentication) {
		return WebSessionAuthenticationRequest.class.isAssignableFrom(authentication);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(idService, "This provider requires an identityDetails service.");
	}
}
