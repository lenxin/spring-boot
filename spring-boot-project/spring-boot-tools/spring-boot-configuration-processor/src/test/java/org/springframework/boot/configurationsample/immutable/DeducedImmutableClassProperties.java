package org.springframework.boot.configurationsample.immutable;

import org.springframework.boot.configurationsample.ConfigurationProperties;
import org.springframework.boot.configurationsample.ConstructorBinding;
import org.springframework.boot.configurationsample.DefaultValue;

/**
 * Inner properties, in immutable format.
 *

 */
@ConfigurationProperties("test")
@ConstructorBinding
public class DeducedImmutableClassProperties {

	private final Nested nested;

	public DeducedImmutableClassProperties(@DefaultValue Nested nested) {
		this.nested = nested;
	}

	public Nested getNested() {
		return this.nested;
	}

	public static class Nested {

		private final String name;

		public Nested(String name) {
			this.name = name;
		}

		public String getName() {
			return this.name;
		}

	}

}
