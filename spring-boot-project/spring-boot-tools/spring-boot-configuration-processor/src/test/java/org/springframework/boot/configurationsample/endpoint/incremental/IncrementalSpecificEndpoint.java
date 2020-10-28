package org.springframework.boot.configurationsample.endpoint.incremental;

import org.springframework.boot.configurationsample.MetaEndpoint;

/**
 * A meta-annotated endpoint similar to {@code @WebEndpoint} or {@code @JmxEndpoint} in
 * Spring Boot.
 *

 */
@MetaEndpoint(id = "incremental")
public class IncrementalSpecificEndpoint {

}
