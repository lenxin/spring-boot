package org.springframework.boot.configurationsample.simple;

import org.springframework.boot.configurationsample.ConfigurationProperties;

/**
 * Class with nested configuration properties.
 *

 */
public class ClassWithNestedProperties {

	public static class NestedParentClass {

		private int parentClassProperty = 10;

		public int getParentClassProperty() {
			return this.parentClassProperty;
		}

		public void setParentClassProperty(int parentClassProperty) {
			this.parentClassProperty = parentClassProperty;
		}

	}

	@ConfigurationProperties(prefix = "nestedChildProps")
	public static class NestedChildClass extends NestedParentClass {

		private int childClassProperty = 20;

		public int getChildClassProperty() {
			return this.childClassProperty;
		}

		public void setChildClassProperty(int childClassProperty) {
			this.childClassProperty = childClassProperty;
		}

	}

}
