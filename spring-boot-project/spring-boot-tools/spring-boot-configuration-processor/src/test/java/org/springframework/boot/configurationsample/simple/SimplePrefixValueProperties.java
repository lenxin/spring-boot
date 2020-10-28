package org.springframework.boot.configurationsample.simple;

import org.springframework.boot.configurationsample.ConfigurationProperties;

/**
 * Properties with a simple prefix.
 *

 */
@ConfigurationProperties("simple")
public class SimplePrefixValueProperties {

	private String name;

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
