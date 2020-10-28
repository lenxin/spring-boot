package org.springframework.boot.actuate.endpoint.web.annotation;

import org.springframework.boot.actuate.endpoint.ExposableEndpoint;
import org.springframework.boot.actuate.endpoint.Operation;
import org.springframework.boot.actuate.endpoint.web.PathMappedEndpoint;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Information describing an endpoint that can be exposed over Spring MVC or Spring
 * WebFlux. Mappings should be discovered directly from {@link #getController()} and
 * {@link #getOperations()} should always return an empty collection.
 *

 * @since 2.0.0
 */
public interface ExposableControllerEndpoint extends ExposableEndpoint<Operation>, PathMappedEndpoint {

	/**
	 * Return the source controller that contains {@link RequestMapping @RequestMapping}
	 * methods.
	 * @return the source controller
	 */
	Object getController();

}
