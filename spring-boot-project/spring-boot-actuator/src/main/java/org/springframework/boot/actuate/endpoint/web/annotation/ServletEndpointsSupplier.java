package org.springframework.boot.actuate.endpoint.web.annotation;

import org.springframework.boot.actuate.endpoint.EndpointsSupplier;
import org.springframework.boot.actuate.endpoint.web.ExposableServletEndpoint;

/**
 * {@link EndpointsSupplier} for {@link ExposableServletEndpoint servlet endpoints}.
 *

 * @since 2.0.0
 */
@FunctionalInterface
public interface ServletEndpointsSupplier extends EndpointsSupplier<ExposableServletEndpoint> {

}
