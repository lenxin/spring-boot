package org.springframework.boot.configurationsample.endpoint.incremental;

import org.springframework.boot.configurationsample.Endpoint;
import org.springframework.boot.configurationsample.ReadOperation;
import org.springframework.lang.Nullable;

/**
 * An endpoint that is enabled by default.
 *

 */
@Endpoint(id = "incremental")
public class IncrementalEndpoint {

	@ReadOperation
	public String invoke(@Nullable String param) {
		return "test";
	}

}
