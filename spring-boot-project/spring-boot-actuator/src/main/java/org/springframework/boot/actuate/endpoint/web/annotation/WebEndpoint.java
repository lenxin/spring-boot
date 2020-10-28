package org.springframework.boot.actuate.endpoint.web.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.FilteredEndpoint;
import org.springframework.core.annotation.AliasFor;

/**
 * Identifies a type as being an endpoint that is only exposed over HTTP.
 *


 * @since 2.0.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Endpoint
@FilteredEndpoint(WebEndpointFilter.class)
public @interface WebEndpoint {

	/**
	 * The id of the endpoint.
	 * @return the id
	 */
	@AliasFor(annotation = Endpoint.class)
	String id();

	/**
	 * If the endpoint should be enabled or disabled by default.
	 * @return {@code true} if the endpoint is enabled by default
	 */
	@AliasFor(annotation = Endpoint.class)
	boolean enableByDefault() default true;

}
