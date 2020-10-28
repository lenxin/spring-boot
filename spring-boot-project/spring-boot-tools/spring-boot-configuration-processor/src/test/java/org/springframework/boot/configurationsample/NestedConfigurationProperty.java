package org.springframework.boot.configurationsample;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Alternative to Spring Boot's {@code @NestedConfigurationProperty} for testing (removes
 * the need for a dependency on the real annotation).
 *


 * @since 1.2.0
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NestedConfigurationProperty {

}
