package org.springframework.boot.configurationsample.specific;

/**
 * A type with more than one constructor.
 *

 */
public class TwoConstructorsExample {

	private String name;

	public TwoConstructorsExample() {
	}

	public TwoConstructorsExample(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
