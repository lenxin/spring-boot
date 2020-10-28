package org.springframework.boot.actuate.endpoint.jmx;

import org.springframework.boot.actuate.endpoint.ExposableEndpoint;

/**
 * Information describing an endpoint that can be exposed over JMX.
 *

 * @since 2.0.0
 */
public interface ExposableJmxEndpoint extends ExposableEndpoint<JmxOperation> {

}
