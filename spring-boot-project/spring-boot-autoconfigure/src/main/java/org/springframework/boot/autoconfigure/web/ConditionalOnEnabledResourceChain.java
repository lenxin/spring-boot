package org.springframework.boot.autoconfigure.web;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Conditional;

/**
 * {@link Conditional @Conditional} that checks whether or not the Spring resource
 * handling chain is enabled. Matches if {@link ResourceProperties.Chain#getEnabled()} is
 * {@code true} or if {@code webjars-locator-core} is on the classpath.
 *

 * @since 1.3.0
 */
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Conditional(OnEnabledResourceChainCondition.class)
public @interface ConditionalOnEnabledResourceChain {

}
