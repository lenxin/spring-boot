package org.springframework.boot.configurationsample.specific;

import org.springframework.boot.configurationsample.ConfigurationProperties;
import org.springframework.boot.configurationsample.NestedConfigurationProperty;

/**
 * Demonstrate the auto-detection of inner config classes.
 *

 */
@ConfigurationProperties(prefix = "config")
public class InnerClassProperties {

	private final Foo first = new Foo();

	private Foo second = new Foo();

	@NestedConfigurationProperty
	private final SimplePojo third = new SimplePojo();

	private Fourth fourth;

	public Foo getFirst() {
		return this.first;
	}

	public Foo getTheSecond() {
		return this.second;
	}

	public void setTheSecond(Foo second) {
		this.second = second;
	}

	public SimplePojo getThird() {
		return this.third;
	}

	public Fourth getFourth() {
		return this.fourth;
	}

	public void setFourth(Fourth fourth) {
		this.fourth = fourth;
	}

	public static class Foo {

		private String name;

		private final Bar bar = new Bar();

		public String getName() {
			return this.name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Bar getBar() {
			return this.bar;
		}

		public static class Bar {

			private String name;

			public String getName() {
				return this.name;
			}

			public void setName(String name) {
				this.name = name;
			}

		}

	}

	public enum Fourth {

		YES, NO

	}

}
