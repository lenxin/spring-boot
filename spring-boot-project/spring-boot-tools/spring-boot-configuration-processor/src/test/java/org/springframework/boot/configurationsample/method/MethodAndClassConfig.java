package org.springframework.boot.configurationsample.method;

import org.springframework.boot.configurationsample.ConfigurationProperties;

/**
 * Sample for testing mixed method and class configuration.
 *

 */
@ConfigurationProperties("conflict")
public class MethodAndClassConfig {

	private String value;

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@ConfigurationProperties(prefix = "conflict")
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
