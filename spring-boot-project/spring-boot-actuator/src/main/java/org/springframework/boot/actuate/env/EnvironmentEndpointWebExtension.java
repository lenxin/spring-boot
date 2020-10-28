package org.springframework.boot.actuate.env;

import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.Selector;
import org.springframework.boot.actuate.endpoint.web.WebEndpointResponse;
import org.springframework.boot.actuate.endpoint.web.annotation.EndpointWebExtension;
import org.springframework.boot.actuate.env.EnvironmentEndpoint.EnvironmentEntryDescriptor;

/**
 * {@link EndpointWebExtension @EndpointWebExtension} for the {@link EnvironmentEndpoint}.
 *


 * @since 2.0.0
 */
@EndpointWebExtension(endpoint = EnvironmentEndpoint.class)
public class EnvironmentEndpointWebExtension {

	private final EnvironmentEndpoint delegate;

	public EnvironmentEndpointWebExtension(EnvironmentEndpoint delegate) {
		this.delegate = delegate;
	}

	@ReadOperation
	public WebEndpointResponse<EnvironmentEntryDescriptor> environmentEntry(@Selector String toMatch) {
		EnvironmentEntryDescriptor descriptor = this.delegate.environmentEntry(toMatch);
		return (descriptor.getProperty() != null) ? new WebEndpointResponse<>(descriptor, WebEndpointResponse.STATUS_OK)
				: new WebEndpointResponse<>(WebEndpointResponse.STATUS_NOT_FOUND);
	}

}
