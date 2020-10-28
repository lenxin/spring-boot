package org.springframework.boot.test.autoconfigure.orm.jpa;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.boot.autoconfigure.ImportAutoConfiguration;

/**
 * Annotation that can be applied to a test class to enable auto-configuration of a
 * {@link TestEntityManager}.
 *

 * @since 1.4.0
 * @see TestEntityManagerAutoConfiguration
 */
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@ImportAutoConfiguration
public @interface AutoConfigureTestEntityManager {

}
