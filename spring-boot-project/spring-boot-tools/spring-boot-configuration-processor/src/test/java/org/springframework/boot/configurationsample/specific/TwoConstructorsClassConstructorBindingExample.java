package org.springframework.boot.configurationsample.specific;

import org.springframework.boot.configurationsample.MetaConstructorBinding;

/**
 * A type that declares constructor binding but with two available constructors.
 *

 */
@MetaConstructorBinding
@SuppressWarnings("unused")
public class TwoConstructorsClassConstructorBindingExample {

	private String name;

	private String description;

	public TwoConstructorsClassConstructorBindingExample(String name) {
		this(name, null);
	}

	public TwoConstructorsClassConstructorBindingExample(String name, String description) {
		this.name = name;
		this.description = description;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
