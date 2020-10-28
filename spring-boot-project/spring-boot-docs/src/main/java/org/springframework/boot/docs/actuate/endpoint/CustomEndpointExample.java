package org.springframework.boot.docs.actuate.endpoint;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;

/**
 * An example of a custom actuator endpoint.
 *

 */
@Endpoint(id = "custom")
public class CustomEndpointExample {

	// tag::read[]
	@ReadOperation
	public CustomData getCustomData() {
		return new CustomData("test", 5);
	}
	// end::read[]

	// tag::write[]
	@WriteOperation
	public void updateCustomData(String name, int counter) {
		// injects "test" and 42
	}
	// end::write[]

	public static class CustomData {

		private final String name;

		private final int counter;

		public CustomData(String name, int counter) {
			this.name = name;
			this.counter = counter;
		}

	}

}
