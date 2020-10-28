package org.springframework.boot.configurationsample.method;

import org.springframework.boot.configurationsample.ConfigurationProperties;

/**
 * Sample for testing invalid method configuration.
 *

 */
@ConfigurationProperties(prefix = "something")
public class InvalidMethodConfig {

	private String name;

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ConfigurationProperties(prefix = "invalid")
	InvalidMethodConfig foo() {
		return new InvalidMethodConfig();
	}

}
