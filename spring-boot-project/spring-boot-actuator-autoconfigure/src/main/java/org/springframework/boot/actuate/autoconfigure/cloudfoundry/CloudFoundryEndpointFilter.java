package org.springframework.boot.actuate.autoconfigure.cloudfoundry;

import org.springframework.boot.actuate.endpoint.EndpointFilter;
import org.springframework.boot.actuate.endpoint.annotation.DiscovererEndpointFilter;

/**
 * {@link EndpointFilter} for endpoints discovered by
 * {@link CloudFoundryWebEndpointDiscoverer}.
 *

 */
class CloudFoundryEndpointFilter extends DiscovererEndpointFilter {

	protected CloudFoundryEndpointFilter() {
		super(CloudFoundryWebEndpointDiscoverer.class);
	}

}
