package org.springframework.boot.configurationsample.simple;

/**
 * Parent for {@link HierarchicalProperties}.
 *

 */
public abstract class HierarchicalPropertiesParent extends HierarchicalPropertiesGrandparent {

	private String second = "two";

	public String getSecond() {
		return this.second;
	}

	public void setSecond(String second) {
		this.second = second;
	}

	// Useless override

	@Override
	public String getFirst() {
		return super.getFirst();
	}

	@Override
	public void setFirst(String first) {
		super.setFirst(first);
	}

}
