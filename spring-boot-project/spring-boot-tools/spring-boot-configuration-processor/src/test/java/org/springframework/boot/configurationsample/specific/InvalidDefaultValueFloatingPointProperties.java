package org.springframework.boot.configurationsample.specific;

import org.springframework.boot.configurationsample.ConfigurationProperties;
import org.springframework.boot.configurationsample.ConstructorBinding;
import org.springframework.boot.configurationsample.DefaultValue;

/**
 * Demonstrates that an invalid default floating point value leads to a compilation
 * failure.
 *

 */
@ConfigurationProperties("test")
@ConstructorBinding
public class InvalidDefaultValueFloatingPointProperties {

	private final Double ratio;

	public InvalidDefaultValueFloatingPointProperties(@DefaultValue("55.55.33") Double ratio) {
		this.ratio = ratio;
	}

	public Double getRatio() {
		return this.ratio;
	}

}
