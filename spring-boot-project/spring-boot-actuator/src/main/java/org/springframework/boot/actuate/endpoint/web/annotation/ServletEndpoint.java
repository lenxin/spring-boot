package org.springframework.boot.actuate.endpoint.web.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.function.Supplier;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.FilteredEndpoint;
import org.springframework.boot.actuate.endpoint.web.EndpointServlet;
import org.springframework.core.annotation.AliasFor;

/**
 * Identifies a type as being an endpoint that supplies a servlet to expose.
 * Implementations must also implement {@link Supplier Supplier&lt;EndpointServlet&gt;}
 * and return a valid {@link EndpointServlet}.
 * <p>
 * This annotation can be used when existing servlets need to be exposed as actuator
 * endpoints, but it is at the expense of portability. Most users should prefer the
 * {@link Endpoint @Endpoint} or {@link WebEndpoint @WebEndpoint} annotations whenever
 * possible.
 *

 * @since 2.0.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Endpoint
@FilteredEndpoint(ServletEndpointFilter.class)
public @interface ServletEndpoint {

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
