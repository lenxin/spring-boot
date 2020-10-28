package org.springframework.boot.actuate.endpoint.jmx;

import org.springframework.boot.actuate.endpoint.EndpointsSupplier;

/**
 * {@link EndpointsSupplier} for {@link ExposableJmxEndpoint JMX endpoints}.
 *

 * @since 2.0.0
 */
@FunctionalInterface
public interface JmxEndpointsSupplier extends EndpointsSupplier<ExposableJmxEndpoint> {

}
