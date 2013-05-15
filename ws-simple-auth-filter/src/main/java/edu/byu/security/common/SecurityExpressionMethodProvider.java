package edu.byu.security.common;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * The SecurityExpressionMethodProvider interface defines providers of Method to be used in a SpeL context.
 *
 * @author Andrew Largey
 */
public interface SecurityExpressionMethodProvider {

	/**
	 * Gets a Map of String names to Method objects that will be added to a SpeL context.
	 * <p/>
	 * The Methods provided must be static, and will be accessed by #{name} in an expression.
	 *
	 * @return Map of String names to Methods to be added to the evaluation context.
	 * @see <a href="http://static.springsource.org/spring/docs/3.0.5.RELEASE/reference/expressions.html#expressions-ref-functions">Spring Expression Language - 6.5.11 Functions</a>
	 */
	Map<String, Method> getMethods();

}
