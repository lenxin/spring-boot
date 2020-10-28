package org.springframework.boot.configurationsample.immutable;

import org.springframework.boot.configurationsample.DefaultValue;

/**
 * Simple immutable properties with primitive wrapper types and defaults.
 *

 */
@SuppressWarnings("unused")
public class ImmutablePrimitiveWrapperWithDefaultsProperties {

	private final Boolean flag;

	private final Byte octet;

	private final Character letter;

	private final Short number;

	private final Integer counter;

	private final Long value;

	private final Float percentage;

	private final Double ratio;

	public ImmutablePrimitiveWrapperWithDefaultsProperties(@DefaultValue("true") Boolean flag,
			@DefaultValue("120") Byte octet, @DefaultValue("a") Character letter, @DefaultValue("1000") Short number,
			@DefaultValue("42") Integer counter, @DefaultValue("2000") Long value,
			@DefaultValue("0.5") Float percentage, @DefaultValue("42.42") Double ratio) {
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
