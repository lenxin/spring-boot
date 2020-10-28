package org.springframework.boot.testsupport.testcontainers;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.junit.jupiter.api.extension.ExtendWith;

/**
 * Disables test execution if Docker is unavailable.
 *


 * @since 2.3.0
 */
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ExtendWith(DisabledIfDockerUnavailableCondition.class)
public @interface DisabledIfDockerUnavailable {

}
