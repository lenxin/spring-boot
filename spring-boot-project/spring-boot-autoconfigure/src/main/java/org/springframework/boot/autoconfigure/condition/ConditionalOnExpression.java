package org.springframework.boot.autoconfigure.condition;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Conditional;

/**
 * Configuration annotation for a conditional element that depends on the value of a SpEL
 * expression.
 *

 * @since 1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.METHOD })
@Documented
@Conditional(OnExpressionCondition.class)
public @interface ConditionalOnExpression {

	/**
	 * The SpEL expression to evaluate. Expression should return {@code true} if the
	 * condition passes or {@code false} if it fails.
	 * @return the SpEL expression
	 */
	String value() default "true";

}
