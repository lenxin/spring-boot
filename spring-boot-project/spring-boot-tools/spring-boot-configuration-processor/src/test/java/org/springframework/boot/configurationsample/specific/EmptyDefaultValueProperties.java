package org.springframework.boot.configurationsample.specific;

import org.springframework.boot.configurationsample.ConfigurationProperties;
import org.springframework.boot.configurationsample.ConstructorBinding;
import org.springframework.boot.configurationsample.DefaultValue;

/**
 * Demonstrates that an empty default value on a property leads to no default value.
 *

 */
@ConfigurationProperties("test")
public class EmptyDefaultValueProperties {

	private final String name;

	@ConstructorBinding
	public EmptyDefaultValueProperties(@DefaultValue String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

}
