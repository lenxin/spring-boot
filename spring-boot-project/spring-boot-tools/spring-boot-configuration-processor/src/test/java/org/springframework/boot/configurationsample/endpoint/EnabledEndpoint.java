package org.springframework.boot.configurationsample.endpoint;

import org.springframework.boot.configurationsample.Endpoint;
import org.springframework.boot.configurationsample.ReadOperation;

/**
 * An endpoint that is enabled unless configured explicitly.
 *

 */
@Endpoint(id = "enabled")
public class EnabledEndpoint {

	public String someMethod() {
		return "not a read operation";
	}

	@ReadOperation
	public String retrieve(String parameter, Integer anotherParameter) {
		return "not a main read operation";
	}

}
