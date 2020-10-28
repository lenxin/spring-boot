package org.springframework.boot.configurationsample.generic;

import org.springframework.boot.configurationsample.ConfigurationProperties;
import org.springframework.boot.configurationsample.NestedConfigurationProperty;

/**
 * More advanced generic setup.
 *

 */
@ConfigurationProperties("generic")
public class ComplexGenericProperties {

	@NestedConfigurationProperty
	private final UpperBoundGenericPojo<Test> test = new UpperBoundGenericPojo<>();

	public UpperBoundGenericPojo<Test> getTest() {
		return this.test;
	}

	public enum Test {

		ONE, TWO, THREE

	}

}
