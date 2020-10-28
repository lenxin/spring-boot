package org.springframework.boot.configurationsample.method;

import org.springframework.boot.configurationsample.ConfigurationProperties;

/**
 * Sample for testing method configuration.
 *

 */
@ConfigurationProperties("something")
public class EmptyTypeMethodConfig {

	private String name;

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ConfigurationProperties("something")
	public Foo foo() {
		return new Foo();
	}

	static class Foo {

	}

}
