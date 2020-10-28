package org.springframework.boot.actuate.endpoint;

import java.util.Collection;

/**
 * Provides access to a collection of {@link ExposableEndpoint endpoints}.
 *
 * @param <E> the endpoint type



 * @since 2.0.0
 */
@FunctionalInterface
public interface EndpointsSupplier<E extends ExposableEndpoint<?>> {

	/**
	 * Return the provided endpoints.
	 * @return the endpoints
	 */
	Collection<E> getEndpoints();

}
