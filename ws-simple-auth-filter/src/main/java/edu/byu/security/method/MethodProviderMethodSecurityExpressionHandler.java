package edu.byu.security.method;

import edu.byu.security.common.SecurityExpressionMethodProvider;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.core.Authentication;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The MethodProviderMethodSecurityExpressionHandler allows for the addition of Methods to the SpeL evaluation context.
 * <p/>
 * This allows for the easy addition of authorization expressions to the security evaluation context using SecurityExpressionMethodProvider classes.
 *
 * @author Andrew Largey
 *
 * @see edu.byu.security.common.SecurityExpressionMethodProvider
 */
public class MethodProviderMethodSecurityExpressionHandler extends DefaultMethodSecurityExpressionHandler implements MethodSecurityExpressionHandler {

	/**
	 * List of SecurityExpressionMethodProvider beans that were injected by Spring.
	 */
	private List<SecurityExpressionMethodProvider> methodProviders;

	/**
	 * Map of Methods provided by the methodProviders to be added the the evaluation context.
	 */
	private Map<String, Object> providedMethods = new HashMap<String, Object>();

	/**
	 * Setter for Injection of SecurityExpressionMethodProvider instances.
	 * @param injectedMethodProviders List of SecurityExpressionMethodProvider instances that will add methods to the evaluation context.
	 */
	@Autowired(required = false)
	public final void setMethodProviders(final List<SecurityExpressionMethodProvider> injectedMethodProviders) {
		this.methodProviders = injectedMethodProviders;
		for (SecurityExpressionMethodProvider methodProvider : this.methodProviders) {
			providedMethods.putAll(methodProvider.getMethods());
		}
		processMethodProviders(methodProviders);
	}

	/**
	 * Allows for processing of the List of SecurityExpressionMethodProvider instances by subclasses.
	 * @param injectedMethodProviders List of SecurityExpressionMethodProvider injected into this class.
	 */
	protected void processMethodProviders(final List<SecurityExpressionMethodProvider> injectedMethodProviders) { }

	/**
	 * Creates the EvaluationContext used by the security expressions.
	 * <p/>
	 * All the methods from providedMethods are added to this context along with the given MethodInvocation object with name "methodInvocation".
	 *
	 *
	 * @param auth Authentication the current authentication.
	 * @param mi MethodInvocation the method that has been interrupted by this security evaluation.
	 * @return EvaluationContext used to evaluate security expressions.
	 */
	@Override
	public final StandardEvaluationContext createEvaluationContextInternal(final Authentication auth, final MethodInvocation mi) {
		StandardEvaluationContext context = super.createEvaluationContextInternal(auth, mi);
		context.setVariable("methodInvocation", mi);
		context.setVariables(providedMethods);
		return context;
	}
}
