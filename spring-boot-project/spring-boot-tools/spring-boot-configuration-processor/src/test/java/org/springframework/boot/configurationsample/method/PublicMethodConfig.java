package org.springframework.boot.configurationsample.method;

import org.springframework.boot.configurationsample.ConfigurationProperties;

/**
 * Sample for testing public method configuration.
 *

 */
public class PublicMethodConfig {

	@ConfigurationProperties(prefix = "foo")
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
