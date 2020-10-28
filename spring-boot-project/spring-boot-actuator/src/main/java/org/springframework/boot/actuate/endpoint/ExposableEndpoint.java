package org.springframework.boot.actuate.endpoint;

import java.util.Collection;

/**
 * Information describing an endpoint that can be exposed in some technology specific way.
 *
 * @param <O> the type of the endpoint's operations


 * @since 2.0.0
 */
public interface ExposableEndpoint<O extends Operation> {

	/**
	 * Return the endpoint ID.
	 * @return the endpoint ID
	 */
	EndpointId getEndpointId();

	/**
	 * Returns if the endpoint is enabled by default.
	 * @return if the endpoint is enabled by default
	 */
	boolean isEnableByDefault();

	/**
	 * Returns the operations of the endpoint.
	 * @return the operations
	 */
	Collection<O> getOperations();

}
