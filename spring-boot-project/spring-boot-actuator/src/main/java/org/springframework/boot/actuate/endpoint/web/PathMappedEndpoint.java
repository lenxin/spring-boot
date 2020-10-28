package org.springframework.boot.actuate.endpoint.web;

import org.springframework.boot.actuate.endpoint.ExposableEndpoint;

/**
 * Interface that can be implemented by an {@link ExposableEndpoint} that is mapped to a
 * root web path.
 *

 * @since 2.0.0
 * @see PathMapper
 */
@FunctionalInterface
public interface PathMappedEndpoint {

	/**
	 * Return the root path of the endpoint, relative to the context that exposes it. For
	 * example, a root path of {@code example} would be exposed under the URL
	 * "/{actuator-context}/example".
	 * @return the root path for the endpoint
	 */
	String getRootPath();

}
