package org.springframework.boot.context.properties;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation that can be used to indicate that configuration properties should be bound
 * using constructor arguments rather than by calling setters. Can be added at the type
 * level (if there is an unambiguous constructor) or on the actual constructor to use.
 *

 * @since 2.2.0
 * @see ConfigurationProperties
 */
@Target({ ElementType.TYPE, ElementType.CONSTRUCTOR })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ConstructorBinding {

}
