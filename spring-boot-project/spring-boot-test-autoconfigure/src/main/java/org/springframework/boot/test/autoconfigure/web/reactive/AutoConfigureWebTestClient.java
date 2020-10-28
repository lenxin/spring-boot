package org.springframework.boot.test.autoconfigure.web.reactive;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.time.Duration;

import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.properties.PropertyMapping;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * Annotation that can be applied to a test class to enable a {@link WebTestClient}. At
 * the moment, only WebFlux applications are supported.
 *

 * @since 2.0.0
 * @see WebTestClientAutoConfiguration
 */
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@ImportAutoConfiguration
@PropertyMapping("spring.test.webtestclient")
public @interface AutoConfigureWebTestClient {

	/**
	 * The timeout duration for the client (in any format handled by
	 * {@link Duration#parse(CharSequence)}).
	 * @return the web client timeout
	 */
	String timeout() default "";

}
