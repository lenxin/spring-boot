package org.springframework.boot.configurationsample.immutable;

import org.springframework.boot.configurationsample.DefaultValue;

/**
 * Simple immutable properties with primitive types and defaults.
 *

 */
@SuppressWarnings("unused")
public class ImmutablePrimitiveWithDefaultsProperties {

	private final boolean flag;

	private final byte octet;

	private final char letter;

	private final short number;

	private final int counter;

	private final long value;

	private final float percentage;

	private final double ratio;

	public ImmutablePrimitiveWithDefaultsProperties(@DefaultValue("true") boolean flag, @DefaultValue("120") byte octet,
			@DefaultValue("a") char letter, @DefaultValue("1000") short number, @DefaultValue("42") int counter,
			@DefaultValue("2000") long value, @DefaultValue("0.5") float percentage,
			@DefaultValue("42.42") double ratio) {
		this.flag = flag;
		this.octet = octet;
		this.letter = letter;
		this.number = number;
		this.counter = counter;
		this.value = value;
		this.percentage = percentage;
		this.ratio = ratio;
	}

}
