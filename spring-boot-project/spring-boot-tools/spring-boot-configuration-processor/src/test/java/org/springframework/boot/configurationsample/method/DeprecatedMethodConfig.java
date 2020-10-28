package org.springframework.boot.configurationsample.method;

import org.springframework.boot.configurationsample.ConfigurationProperties;

/**
 * Sample for testing deprecated method configuration.
 *

 */
public class DeprecatedMethodConfig {

	@ConfigurationProperties(prefix = "foo")
	@Deprecated
	public Foo foo() {
		return new Foo();
	}

	public static class Foo {

		private String name;

		private boolean flag;

		public String getName() {
			return this.name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public boolean isFlag() {
			return this.flag;
		}

		public void setFlag(boolean flag) {
			this.flag = flag;
		}

	}

}
