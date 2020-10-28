package org.springframework.boot.configurationsample.specific;

import org.springframework.boot.configurationsample.ConfigurationProperties;
import org.springframework.boot.configurationsample.DefaultValue;
import org.springframework.boot.configurationsample.MetaConstructorBinding;

/**
 * Demonstrates that an invalid default number value leads to a compilation failure.
 *

 */
@ConfigurationProperties("test")
@MetaConstructorBinding
public class InvalidDefaultValueNumberProperties {

	private final int counter;

	public InvalidDefaultValueNumberProperties(@DefaultValue("invalid") int counter) {
		this.counter = counter;
	}

	public int getCounter() {
		return this.counter;
	}

}
