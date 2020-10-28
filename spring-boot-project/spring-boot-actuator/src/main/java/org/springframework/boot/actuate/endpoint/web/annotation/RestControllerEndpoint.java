package org.springframework.boot.actuate.endpoint.web.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.boot.actuate.endpoint.annotation.DeleteOperation;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.FilteredEndpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.core.annotation.AliasFor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Identifies a type as being a REST endpoint that is only exposed over Spring MVC or
 * Spring WebFlux. Mapped methods must be annotated with {@link GetMapping @GetMapping},
 * {@link PostMapping @PostMapping}, {@link DeleteMapping @DeleteMapping}, etc annotations
 * rather than {@link ReadOperation @ReadOperation},
 * {@link WriteOperation @WriteOperation}, {@link DeleteOperation @DeleteOperation}.
 * <p>
 * This annotation can be used when deeper Spring integration is required, but at the
 * expense of portability. Most users should prefer the {@link Endpoint @Endpoint} or
 * {@link WebEndpoint @WebEndpoint} annotations whenever possible.
 *

 * @since 2.0.0
 * @see WebEndpoint
 * @see ControllerEndpoint
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Endpoint
@FilteredEndpoint(ControllerEndpointFilter.class)
@ResponseBody
public @interface RestControllerEndpoint {

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
