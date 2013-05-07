package edu.byu.auth.filter;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.Filter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Author: Wyatt Taylor (wyatt_taylor@byu.edu)
 * Date: 05/07/2013
 *
 * @author Wyatt Taylor (wyatt_taylor@byu.edu)
 * @since 05/07/2013
 */
public class SimpleAuthFilter extends AbstractAuthenticationProcessingFilter implements Filter {

	public SimpleAuthFilter(final String defaultFilterProcessesUrl) {
		super(defaultFilterProcessesUrl);
	}

	@Override
	public Authentication attemptAuthentication(final HttpServletRequest request, final HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {

		return null;
	}
}
