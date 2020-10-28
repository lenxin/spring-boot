package org.springframework.boot.actuate.autoconfigure.cloudfoundry.servlet;

import java.util.Map;

import org.springframework.boot.actuate.autoconfigure.cloudfoundry.EndpointCloudFoundryExtension;
import org.springframework.boot.actuate.endpoint.annotation.EndpointExtension;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.info.InfoEndpoint;

/**
 * {@link EndpointExtension @EndpointExtension} for the {@link InfoEndpoint} that always
 * exposes full git details.
 *

 * @since 2.2.0
 */
@EndpointCloudFoundryExtension(endpoint = InfoEndpoint.class)
public class CloudFoundryInfoEndpointWebExtension {

	private final InfoEndpoint delegate;

	public CloudFoundryInfoEndpointWebExtension(InfoEndpoint delegate) {
		this.delegate = delegate;
	}

	@ReadOperation
	public Map<String, Object> info() {
		return this.delegate.info();
	}

}
