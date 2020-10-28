package org.springframework.boot.actuate.autoconfigure.cloudfoundry;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.EndpointExtension;

/**
 * Identifies a type as being a Cloud Foundry specific extension for an
 * {@link Endpoint @Endpoint}.
 *


 * @since 2.2.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@EndpointExtension(filter = CloudFoundryEndpointFilter.class)
public @interface EndpointCloudFoundryExtension {

	/**
	 * The class of the endpoint to provide a Cloud Foundry specific extension for.
	 * @return the class of the endpoint to extend
	 */
	Class<?> endpoint();

}
