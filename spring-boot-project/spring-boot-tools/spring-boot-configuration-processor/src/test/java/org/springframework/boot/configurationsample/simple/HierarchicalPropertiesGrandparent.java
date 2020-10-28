package org.springframework.boot.configurationsample.simple;

/**
 * Grandparent for {@link HierarchicalProperties}.
 *

 */
public abstract class HierarchicalPropertiesGrandparent {

	private String first = "one";

	public String getFirst() {
		return this.first;
	}

	public void setFirst(String first) {
		this.first = first;
	}

}
