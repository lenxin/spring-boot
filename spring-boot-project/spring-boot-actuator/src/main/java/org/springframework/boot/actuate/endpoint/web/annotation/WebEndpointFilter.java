package org.springframework.boot.actuate.endpoint.web.annotation;

import org.springframework.boot.actuate.endpoint.EndpointFilter;
import org.springframework.boot.actuate.endpoint.annotation.DiscovererEndpointFilter;

/**
 * {@link EndpointFilter} for endpoints discovered by {@link WebEndpointDiscoverer}.
 *

 */
class WebEndpointFilter extends DiscovererEndpointFilter {

	WebEndpointFilter() {
		super(WebEndpointDiscoverer.class);
	}

}
