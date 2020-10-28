package org.springframework.boot.configurationsample.endpoint;

import org.springframework.boot.configurationsample.Endpoint;

/**
 * An endpoint that is disabled unless configured explicitly.
 *

 */
@Endpoint(id = "disabled", enableByDefault = false)
public class DisabledEndpoint {

}
