package edu.byu.security.infoArea;

import edu.byu.security.common.SecurityExpressionMethodProvider;
import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * The InfoAreaExpressionMethodProvider is a SecurityExpressionMethodProvider that adds methods for making InfoAreaGrantedAuthority
 * based authorization decisions to the evaluation context.
 * <p/>
 * The methods provided are:
 * <ul>
 *   <li>#hasAllInfoAreas</li>
 *   <li>#hasAnyInfoArea</li>
 *   <li>#hasInfoArea</li>
 * </ul>
 *
 * @author Andrew Largey
 */
public final class InfoAreaExpressionMethodProvider implements SecurityExpressionMethodProvider {

	/**
	 * Log4J logger used to log for this Class.
	 * <p/>
	 * The logger name is the class name. To enable logging set the log level to DEBUG.
	 */
	private static final Logger LOG = Logger.getLogger(InfoAreaExpressionMethodProvider.class);

	/**
	 * Map of methods provided by this SecurityExpressionMethodProvider.
	 */
	private final Map<String, Method> providedMethods;

	/**
	 * Constructs an InfoAreaExpressionMethodProvider and adds its methods to the providedMethods map.
	 * @throws NoSuchMethodException when there is an error registering a method.
	 */
	public InfoAreaExpressionMethodProvider() throws NoSuchMethodException {
		providedMethods = new HashMap<String, Method>();
		providedMethods.put("hasAllInfoAreas", getClass().getMethod("hasAllInfoAreas", Array.newInstance(String.class, 0).getClass()));
		providedMethods.put("hasAnyInfoArea", getClass().getMethod("hasAnyInfoArea", Array.newInstance(String.class, 0).getClass()));
		providedMethods.put("hasInfoArea", getClass().getMethod("hasInfoArea", String.class));
		if (LOG.isDebugEnabled()) {
			LOG.debug("Registering the following methods in the Security Expression Context:");
			for (Map.Entry<String, Method> stringMethodEntry : providedMethods.entrySet()) {
				LOG.debug(stringMethodEntry.getKey());
			}
		}
	}

	/**
	 * Determines if the current Identity has all the Info Areas represented by the input authorityStrings.
	 * @param authorityStrings Var-Arg String value(s) representing the authorization a user must have.
	 * @return boolean true if the current Identity has all the authorizations, false otherwise.
	 * @throws edu.byu.security.infoArea.InfoAreaGrantedAuthorityParseException when there is an error parsing the InfoAreaGrantedAuthority Strings.
	 */
	public static boolean hasAllInfoAreas(final String... authorityStrings) throws InfoAreaGrantedAuthorityParseException {
		validateInput(authorityStrings);
		if (LOG.isDebugEnabled()) {
			LOG.debug("Checking if current user has all required info areas.");
		}
		for (String authorityString : authorityStrings) {
			if (!hasInfoArea(authorityString)) {
				if (LOG.isDebugEnabled()) {
					LOG.debug("Access denied, user does not have info area [" + authorityString + "].");
				}
				return false;
			}
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("Access granted.");
		}
		return true;
	}

	/**
	 * Determines if the current Identity has any of the Info Areas represented by the input authorityStrings.
	 * @param authorityStrings Var-Arg String value(s) representing the authorization a user must have.
	 * @return boolean true if the current Identity has any of the authorizations, false otherwise.
	 * @throws edu.byu.security.infoArea.InfoAreaGrantedAuthorityParseException when there is an error parsing the InfoAreaGrantedAuthority Strings.
	 */
	public static boolean hasAnyInfoArea(final String... authorityStrings) throws InfoAreaGrantedAuthorityParseException {
		validateInput(authorityStrings);
		if (LOG.isDebugEnabled()) {
			LOG.debug("Checking if current user has any required info area.");
		}
		for (String authorityString : authorityStrings) {
			if (hasInfoArea(authorityString)) {
				if (LOG.isDebugEnabled()) {
					LOG.debug("Access granted, user had info area [" + authorityString + "].");
				}
				return true;
			}
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("Access denied.");
		}
		return false;
	}

	/**
	 * Validates that there input is not null and has at least one argument.
	 * @param input Var-Arg String(s) to be validated.
	 * @throws IllegalArgumentException when the input is null or has 0 length.
	 */
	private static void validateInput(final String... input) {
		if (input == null || input.length == 0) {
			throw new IllegalArgumentException("Info Area representations are required to evaluate user authorization.");
		}
	}

	/**
	 * Determines if the current Identity has the Info Area represented by the input authorityString.
	 * @param authorityString String value representing the authorization a user must have.
	 * @return boolean true if the current Identity has the authorization, false otherwise.
	 * @throws edu.byu.security.infoArea.InfoAreaGrantedAuthorityParseException when there is an error parsing the InfoAreaGrantedAuthority String.
	 */
	public static boolean hasInfoArea(final String authorityString) throws InfoAreaGrantedAuthorityParseException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication != null && hasInfoArea(authentication, authorityString);
	}

	/**
	 * Determines if the Identity contained by the authentication has the Info Area represented by the input authorityString.
	 * @param authentication Authentication containing the Identity to check the authorizations against.
	 * @param authorityString String value representing the authorization the user must have.
	 * @return boolean true if the Identity has the authorization, false otherwise.
	 * @throws edu.byu.security.infoArea.InfoAreaGrantedAuthorityParseException when there is an error parsing the InfoAreaGrantedAuthority String.
	 */
	public static boolean hasInfoArea(final Authentication authentication, final String authorityString) throws InfoAreaGrantedAuthorityParseException {
		if (authorityString == null || authorityString.length() == 0 || !InfoAreaGrantedAuthority.isAthensGrantedAuthority(authorityString)) {
			throw new IllegalArgumentException("The provided String is not a valid Info Area representation.");
		}
		InfoAreaGrantedAuthority targetAuthority = InfoAreaGrantedAuthority.parseAuthority(authorityString);
		Collection<? extends GrantedAuthority> grantedAuthorities = authentication.getAuthorities();
		if (LOG.isDebugEnabled()) {
			LOG.debug("Checking if current user has info area [" + authorityString + "].");
		}
		for (GrantedAuthority grantedAuthority : grantedAuthorities) {
			if (InfoAreaGrantedAuthority.class.isAssignableFrom(grantedAuthority.getClass())) {
				if (targetAuthority.hasAuthorization(((InfoAreaGrantedAuthority) grantedAuthority))) {
					if (LOG.isDebugEnabled()) {
						LOG.debug("Access granted, user has authority [" + grantedAuthority.getAuthority() + "] which matched [" + authorityString + "].");
					}
					return true;
				}
			}
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("Access denied.");
		}
		return false;
	}

	@Override
	public Map<String, Method> getMethods() {
		return providedMethods;
	}
}
