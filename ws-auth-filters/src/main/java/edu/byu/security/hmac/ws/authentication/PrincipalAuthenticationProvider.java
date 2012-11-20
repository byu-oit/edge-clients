package edu.byu.security.hmac.ws.authentication;

import edu.byu.security.authentication.IdentityBasedAuthenticationResult;
import edu.byu.security.authentication.PrincipalAuthenticationRequest;
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

import java.util.regex.Pattern;

/**
 * Author: Wyatt Taylor (wyatt_taylor@byu.edu)
 * Date: 11/13/2012
 *
 * @author Wyatt Taylor (wyatt_taylor@byu.edu)
 * @since 11/13/2012
 */
public class PrincipalAuthenticationProvider implements AuthenticationProvider, InitializingBean {

	private static final Logger LOG = Logger.getLogger(PrincipalAuthenticationProvider.class);
	private static final Pattern PERSON_ID_PATTERN = Pattern.compile("^\\d{3}2\\d{4}2$");
	private static final Pattern NET_ID_PATTERN = Pattern.compile("^[a-z]{1}[a-z0-9]*?$");

	//autowired
	private IdentityDetailsService idService;

	public void setIdService(final IdentityDetailsService idService) {
		this.idService = idService;
	}

	@Override
	public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
		if (authentication == null || !supports(authentication.getClass())) return null;
		final PrincipalAuthenticationRequest req = (PrincipalAuthenticationRequest) authentication;
		IdentityDetails id;
		try {
			final String principal = req.getPrincipal();
			if (NET_ID_PATTERN.matcher(principal).matches()) {
				id = (IdentityDetails) idService.loadUserByUsername(principal);
			} else {
				id = (IdentityDetails) idService.loadUserDetails(req);
			}
		} catch (final DataAccessException e) {
			throw new AuthenticationServiceException("An error occurred while retrieving the identity.", e);
		} catch (final Exception e) {
			throw new AuthenticationServiceException("An unexpected error has occurred.", e);
		}
		return new IdentityBasedAuthenticationResult(id, req, true);
	}

	@Override
	public boolean supports(final Class<?> authentication) {
		return PrincipalAuthenticationRequest.class.isAssignableFrom(authentication);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(idService, "This provider requires an identityDetails service.");
	}
}
