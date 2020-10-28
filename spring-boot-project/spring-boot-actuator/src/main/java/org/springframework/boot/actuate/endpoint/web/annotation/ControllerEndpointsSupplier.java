package org.springframework.boot.actuate.endpoint.web.annotation;

import org.springframework.boot.actuate.endpoint.EndpointsSupplier;

/**
 * {@link EndpointsSupplier} for {@link ExposableControllerEndpoint controller endpoints}.
 *

 * @since 2.0.0
 */
@FunctionalInterface
public interface ControllerEndpointsSupplier extends EndpointsSupplier<ExposableControllerEndpoint> {

}
