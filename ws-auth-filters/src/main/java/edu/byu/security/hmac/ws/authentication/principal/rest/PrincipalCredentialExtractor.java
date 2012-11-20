package edu.byu.security.hmac.ws.authentication.principal.rest;

import edu.byu.security.authentication.PrincipalAuthenticationRequest;
import edu.byu.security.hmac.ws.authentication.principal.PrincipalCredential;
import edu.byu.security.ws.authentication.rest.HttpServletRequestCredentialExtractor;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Author: Wyatt Taylor (wyatt_taylor@byu.edu)
 * Date: 11/13/2012
 *
 * @author Wyatt Taylor (wyatt_taylor@byu.edu)
 * @since 11/13/2012
 */
public class PrincipalCredentialExtractor implements HttpServletRequestCredentialExtractor<PrincipalAuthenticationRequest> {

	private static final List<String> HEADER_NAMES = Collections.unmodifiableList(Arrays.asList(
			PrincipalCredential.SM_USER,
			PrincipalCredential.ACTOR_ID,
			PrincipalCredential.PRINCIPAL_ID
	));

	@Override
	public PrincipalAuthenticationRequest extractAuthenticationRequest(final HttpServletRequest req) {
		for (final String name : HEADER_NAMES) {
			if (notNullHeaderExists(req, name)) {
				final String header = getHeader(req, name);
				if (! "".equals(header))
					return new PrincipalAuthenticationRequest(new PrincipalCredential(header));
			}
		}
		return null;
	}

	@Override
	public boolean canExtractCredential(final HttpServletRequest httpServletRequest) {
		return notNullHeaderExists(httpServletRequest, PrincipalCredential.WS_AUTH_TYPE);
	}

	@Override
	public boolean consumesRequestBody() {
		return false;
	}

	@Override
	public BodyType consumesBodyAs(final HttpServletRequest request) {
		return BodyType.NONE;
	}

	private static boolean notNullHeaderExists(final HttpServletRequest req, final String headerName) {
		return req != null && req.getHeader(headerName) != null;
	}

	private static String getHeader(final HttpServletRequest req, final String name) {
		if (!notNullHeaderExists(req, name)) return "";
		return req.getHeader(name);
	}
}
