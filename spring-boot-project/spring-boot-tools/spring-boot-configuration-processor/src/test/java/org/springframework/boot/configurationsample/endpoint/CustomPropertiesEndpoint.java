package org.springframework.boot.configurationsample.endpoint;

import org.springframework.boot.configurationsample.ConfigurationProperties;
import org.springframework.boot.configurationsample.Endpoint;
import org.springframework.boot.configurationsample.ReadOperation;

/**
 * An endpoint with additional custom properties.
 *

 */
@Endpoint(id = "customprops")
@ConfigurationProperties("management.endpoint.customprops")
public class CustomPropertiesEndpoint {

	private String name = "test";

	@ReadOperation
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
