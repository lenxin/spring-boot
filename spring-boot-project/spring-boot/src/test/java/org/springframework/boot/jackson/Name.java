package org.springframework.boot.jackson;

/**
 * Sample object used for tests.
 *

 */
public class Name {

	protected final String name;

	public Name(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

}
