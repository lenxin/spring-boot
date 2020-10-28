package org.springframework.boot.test.autoconfigure.web.client;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.properties.PropertyMapping;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

/**
 * Annotation that can be applied to a test class to enable and configure
 * auto-configuration of web clients.
 *


 * @since 1.4.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@ImportAutoConfiguration
@PropertyMapping("spring.test.webclient")
public @interface AutoConfigureWebClient {

	/**
	 * If a {@link RestTemplate} bean should be registered. Defaults to {@code false} with
	 * the assumption that the {@link RestTemplateBuilder} will be used.
	 * @return if a {@link RestTemplate} bean should be added.
	 */
	boolean registerRestTemplate() default false;

}
