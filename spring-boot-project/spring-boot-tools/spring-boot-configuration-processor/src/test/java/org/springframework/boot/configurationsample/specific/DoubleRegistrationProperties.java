package org.springframework.boot.configurationsample.specific;

import org.springframework.boot.configurationsample.ConfigurationProperties;

/**
 * Test that the same type can be registered several times if the prefix is different.
 *

 */
public class DoubleRegistrationProperties {

	@ConfigurationProperties("one")
	public SimplePojo one() {
		return new SimplePojo();
	}

	@ConfigurationProperties("two")
	public SimplePojo two() {
		return new SimplePojo();
	}

}
