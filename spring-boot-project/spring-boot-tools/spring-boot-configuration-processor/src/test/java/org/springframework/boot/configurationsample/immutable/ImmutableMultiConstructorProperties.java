package org.springframework.boot.configurationsample.immutable;

import org.springframework.boot.configurationsample.ConstructorBinding;

/**
 * Simple immutable properties with several constructors.
 *

 */
@SuppressWarnings("unused")
public class ImmutableMultiConstructorProperties {

	private final String name;

	/**
	 * Test description.
	 */
	private final String description;

	public ImmutableMultiConstructorProperties(String name) {
		this(name, null);
	}

	@ConstructorBinding
	public ImmutableMultiConstructorProperties(String name, String description) {
		this.name = name;
		this.description = description;
	}

}
