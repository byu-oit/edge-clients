package edu.byu.security.method;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.security.access.expression.method.ExpressionBasedAnnotationAttributeFactory;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.core.Authentication;

/**
 * The SpeLBasedAnnotationAttributeFactory is used to configure Spring Security to use SpeL based expressions.
 *
 * @author Andrew Largey
 *
 */
public final class SpeLBasedAnnotationAttributeFactory extends ExpressionBasedAnnotationAttributeFactory {

	/**
	 * Constructs the SpeLBasedAnnotationAttributeFactory.
	 */
	public SpeLBasedAnnotationAttributeFactory() {
		super(new MethodSecurityExpressionHandler() {
			@Override
			public ExpressionParser getExpressionParser() {
				return new SpelExpressionParser();
			}

			@Override
			public EvaluationContext createEvaluationContext(final Authentication authentication, final MethodInvocation mi) {
				return null;
			}

			@Override
			public Object filter(final Object filterTarget, final Expression filterExpression, final EvaluationContext ctx) {
				return null;
			}

			@Override
			public void setReturnObject(final Object returnObject, final EvaluationContext ctx) {
			}
		});
	}
}
