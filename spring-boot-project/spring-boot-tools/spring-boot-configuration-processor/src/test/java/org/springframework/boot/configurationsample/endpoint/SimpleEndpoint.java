package org.springframework.boot.configurationsample.endpoint;

import org.springframework.boot.configurationsample.Endpoint;
import org.springframework.boot.configurationsample.ReadOperation;

/**
 * A simple endpoint with no default override.
 *

 */
@Endpoint(id = "simple")
public class SimpleEndpoint {

	@ReadOperation
	public String invoke() {
		return "test";
	}

}
