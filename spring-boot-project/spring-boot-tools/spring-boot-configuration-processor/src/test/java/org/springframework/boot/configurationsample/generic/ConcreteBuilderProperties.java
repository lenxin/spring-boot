package org.springframework.boot.configurationsample.generic;

import org.springframework.boot.configurationsample.ConfigurationProperties;

/**
 * Builder pattern with a resolved generic
 *

 */
@ConfigurationProperties("builder")
public class ConcreteBuilderProperties extends GenericBuilderProperties<ConcreteBuilderProperties> {

	private String description;

	public String getDescription() {
		return this.description;
	}

	public ConcreteBuilderProperties setDescription(String description) {
		this.description = description;
		return this;
	}

}
