package org.springframework.boot.configurationsample.simple;

import org.springframework.boot.configurationsample.ConfigurationProperties;

/**
 * Configuration properties with a single deprecated element.
 *

 */
@ConfigurationProperties("singlefielddeprecated")
public class DeprecatedFieldSingleProperty {

	@Deprecated
	private String name;

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
