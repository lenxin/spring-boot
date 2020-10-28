package org.springframework.boot.configurationsample.lombok;

import lombok.Data;

import org.springframework.boot.configurationsample.ConfigurationProperties;
import org.springframework.boot.configurationsample.NestedConfigurationProperty;

/**
 * Demonstrate the auto-detection of inner config classes using Lombok.
 *

 */
@Data
@ConfigurationProperties(prefix = "config")
@SuppressWarnings("unused")
public class LombokInnerClassProperties {

	private final Foo first = new Foo();

	private Foo second = new Foo();

	@NestedConfigurationProperty
	private final SimpleLombokPojo third = new SimpleLombokPojo();

	private Fourth fourth;

	// Only there to record the source method
	public SimpleLombokPojo getThird() {
		return this.third;
	}

	@Data
	public static class Foo {

		private String name;

		private final Bar bar = new Bar();

		@Data
		public static class Bar {

			private String name;

		}

	}

	public enum Fourth {

		YES, NO

	}

}
