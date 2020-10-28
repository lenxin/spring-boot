package org.springframework.boot.configurationsample.simple;

import org.springframework.boot.configurationsample.ConfigurationProperties;
import org.springframework.boot.configurationsample.DeprecatedConfigurationProperty;

/**
 * Configuration properties with a single deprecated element.
 *

 */
@ConfigurationProperties("singledeprecated")
public class DeprecatedSingleProperty {

	private String newName;

	@Deprecated
	@DeprecatedConfigurationProperty(reason = "renamed", replacement = "singledeprecated.new-name")
	public String getName() {
		return getNewName();
	}

	@Deprecated
	public void setName(String name) {
		setNewName(name);
	}

	public String getNewName() {
		return this.newName;
	}

	public void setNewName(String newName) {
		this.newName = newName;
	}

}
