package org.springframework.boot.configurationsample.simple;

import org.springframework.boot.configurationsample.ConfigurationProperties;

/**
 * Deprecated configuration properties.
 *

 */
@Deprecated
@ConfigurationProperties(prefix = "deprecated")
public class DeprecatedProperties {

	private String name;

	private String description;

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
