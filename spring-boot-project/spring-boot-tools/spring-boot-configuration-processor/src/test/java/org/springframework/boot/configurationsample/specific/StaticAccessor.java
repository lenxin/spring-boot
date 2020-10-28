package org.springframework.boot.configurationsample.specific;

import org.springframework.boot.configurationsample.ConfigurationProperties;

/**
 * A property that is exposed by static accessors.
 *

 */
@ConfigurationProperties("specific")
public class StaticAccessor {

	private static String name;

	private int counter = 42;

	public static String getName() {
		return name;
	}

	public static void setName(String name) {
		StaticAccessor.name = name;
	}

	public int getCounter() {
		return this.counter;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}

}
