package org.springframework.boot.configurationsample.simple;

import org.springframework.boot.configurationsample.ConfigurationProperties;

/**
 * Configuration properties with inherited values.
 *

 */
@ConfigurationProperties(prefix = "hierarchical")
public class HierarchicalProperties extends HierarchicalPropertiesParent {

	private String third = "three";

	public String getThird() {
		return this.third;
	}

	public void setThird(String third) {
		this.third = third;
	}

}
