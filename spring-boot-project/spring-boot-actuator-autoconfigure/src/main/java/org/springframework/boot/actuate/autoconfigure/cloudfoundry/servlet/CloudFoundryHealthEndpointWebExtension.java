package org.springframework.boot.actuate.autoconfigure.cloudfoundry.servlet;

import org.springframework.boot.actuate.autoconfigure.cloudfoundry.EndpointCloudFoundryExtension;
import org.springframework.boot.actuate.endpoint.SecurityContext;
import org.springframework.boot.actuate.endpoint.annotation.EndpointExtension;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.Selector;
import org.springframework.boot.actuate.endpoint.annotation.Selector.Match;
import org.springframework.boot.actuate.endpoint.http.ApiVersion;
import org.springframework.boot.actuate.endpoint.web.WebEndpointResponse;
import org.springframework.boot.actuate.health.HealthComponent;
import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.boot.actuate.health.HealthEndpointWebExtension;

/**
 * {@link EndpointExtension @EndpointExtension} for the {@link HealthEndpoint} that always
 * exposes full health details.
 *

 * @since 2.0.0
 */
@EndpointCloudFoundryExtension(endpoint = HealthEndpoint.class)
public class CloudFoundryHealthEndpointWebExtension {

	private final HealthEndpointWebExtension delegate;

	public CloudFoundryHealthEndpointWebExtension(HealthEndpointWebExtension delegate) {
		this.delegate = delegate;
	}

	@ReadOperation
	public WebEndpointResponse<HealthComponent> health(ApiVersion apiVersion) {
		return this.delegate.health(apiVersion, SecurityContext.NONE, true);
	}

	@ReadOperation
	public WebEndpointResponse<HealthComponent> health(ApiVersion apiVersion,
			@Selector(match = Match.ALL_REMAINING) String... path) {
		return this.delegate.health(apiVersion, SecurityContext.NONE, true, path);
	}

}
