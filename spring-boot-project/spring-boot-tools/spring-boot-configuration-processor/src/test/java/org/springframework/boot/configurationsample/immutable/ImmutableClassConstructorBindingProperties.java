package org.springframework.boot.configurationsample.immutable;

import org.springframework.boot.configurationsample.MetaConstructorBinding;

/**
 * Simple immutable properties with several constructors.
 *

 */
@SuppressWarnings("unused")
@MetaConstructorBinding
public class ImmutableClassConstructorBindingProperties {

	private final String name;

	private final String description;

	public ImmutableClassConstructorBindingProperties(String name, String description) {
		this.name = name;
		this.description = description;
	}

}
