package org.springframework.boot.configurationsample.specific;

import org.springframework.boot.configurationsample.ConfigurationProperties;
import org.springframework.boot.configurationsample.ConstructorBinding;
import org.springframework.boot.configurationsample.DefaultValue;

/**
 * Demonstrates that an invalid default character value leads to a compilation failure.
 *

 */
@ConfigurationProperties("test")
public class InvalidDefaultValueCharacterProperties {

	private final char letter;

	@ConstructorBinding
	public InvalidDefaultValueCharacterProperties(@DefaultValue("bad") char letter) {
		this.letter = letter;
	}

	public char getLetter() {
		return this.letter;
	}

}
