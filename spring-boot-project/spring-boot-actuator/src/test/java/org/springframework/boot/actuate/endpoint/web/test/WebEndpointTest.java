package org.springframework.boot.actuate.endpoint.web.test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;

/**
 * Signals that a test should be performed against all web endpoint implementations
 * (Jersey, Web MVC, and WebFlux)
 *

 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@TestTemplate
@ExtendWith(WebEndpointTestInvocationContextProvider.class)
public @interface WebEndpointTest {

}
