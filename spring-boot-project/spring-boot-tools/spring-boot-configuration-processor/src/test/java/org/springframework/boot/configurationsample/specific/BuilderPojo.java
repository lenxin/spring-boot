package org.springframework.boot.configurationsample.specific;

import org.springframework.boot.configurationsample.ConfigurationProperties;

/**
 * Sample with builder style setters.
 *

 */
@ConfigurationProperties(prefix = "builder")
public class BuilderPojo {

	private String name;

	public String getName() {
		return this.name;
	}

	public BuilderPojo setName(String name) {
		this.name = name;
		return this;
	}

}
