package org.springframework.boot.actuate.endpoint.web.annotation;

import java.util.Collection;

import org.springframework.boot.actuate.endpoint.EndpointId;
import org.springframework.boot.actuate.endpoint.annotation.AbstractDiscoveredEndpoint;
import org.springframework.boot.actuate.endpoint.annotation.EndpointDiscoverer;
import org.springframework.boot.actuate.endpoint.web.ExposableWebEndpoint;
import org.springframework.boot.actuate.endpoint.web.WebOperation;

/**
 * A discovered {@link ExposableWebEndpoint web endpoint}.
 *

 */
class DiscoveredWebEndpoint extends AbstractDiscoveredEndpoint<WebOperation> implements ExposableWebEndpoint {

	private final String rootPath;

	DiscoveredWebEndpoint(EndpointDiscoverer<?, ?> discoverer, Object endpointBean, EndpointId id, String rootPath,
			boolean enabledByDefault, Collection<WebOperation> operations) {
		super(discoverer, endpointBean, id, enabledByDefault, operations);
		this.rootPath = rootPath;
	}

	@Override
	public String getRootPath() {
		return this.rootPath;
	}

}
