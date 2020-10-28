package org.springframework.boot.autoconfigureprocessor;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Alternative to Spring Boot's {@code ConditionalOnSingleCandidate} for testing (removes
 * the need for a dependency on the real annotation).
 *

 */
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TestConditionalOnSingleCandidate {

	Class<?> value() default Object.class;

	String type() default "";

}
