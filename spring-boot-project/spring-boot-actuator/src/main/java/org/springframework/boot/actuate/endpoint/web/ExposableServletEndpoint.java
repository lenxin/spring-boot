package org.springframework.boot.actuate.endpoint.web;

import org.springframework.boot.actuate.endpoint.ExposableEndpoint;
import org.springframework.boot.actuate.endpoint.Operation;

/**
 * Information describing an endpoint that can be exposed by registering a servlet.
 *

 * @since 2.0.0
 */
public interface ExposableServletEndpoint extends ExposableEndpoint<Operation>, PathMappedEndpoint {

	/**
	 * Return details of the servlet that should registered.
	 * @return the endpoint servlet
	 */
	EndpointServlet getEndpointServlet();

}
