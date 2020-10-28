package org.springframework.boot.configurationsample.specific;

import org.springframework.boot.configurationsample.ConfigurationProperties;

/**
 * Test that compilation fails if the same type is registered twice with the same prefix.
 *

 */
public class InvalidDoubleRegistrationProperties {

	@ConfigurationProperties("foo")
	public Foo foo() {
		return new Foo();
	}

	@ConfigurationProperties("foo")
	public static class Foo {

		private String name;

		public String getName() {
			return this.name;
		}

		public void setName(String name) {
			this.name = name;
		}

	}

}
