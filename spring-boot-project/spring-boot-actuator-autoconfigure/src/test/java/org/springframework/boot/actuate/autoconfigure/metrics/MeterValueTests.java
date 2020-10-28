package org.springframework.boot.actuate.autoconfigure.metrics;

import io.micrometer.core.instrument.Meter.Type;
import org.junit.jupiter.api.Test;

import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.mock.env.MockEnvironment;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link MeterValue}.
 *


 */
class MeterValueTests {

	@Test
	void getValueForDistributionSummaryWhenFromNumberShouldReturnDoubleValue() {
		MeterValue meterValue = MeterValue.valueOf(123.42);
		assertThat(meterValue.getValue(Type.DISTRIBUTION_SUMMARY)).isEqualTo(123.42);
	}

	@Test
	void getValueForDistributionSummaryWhenFromNumberStringShouldReturnDoubleValue() {
		MeterValue meterValue = MeterValue.valueOf("123");
		assertThat(meterValue.getValue(Type.DISTRIBUTION_SUMMARY)).isEqualTo(123);
	}

	@Test
	void getValueForDistributionSummaryWhenFromDurationStringShouldReturnNull() {
		MeterValue meterValue = MeterValue.valueOf("123ms");
		assertThat(meterValue.getValue(Type.DISTRIBUTION_SUMMARY)).isNull();
	}

	@Test
	void getValueForTimerWhenFromNumberShouldReturnMsToNanosValue() {
		MeterValue meterValue = MeterValue.valueOf(123d);
		assertThat(meterValue.getValue(Type.TIMER)).isEqualTo(123000000);
	}

	@Test
	void getValueForTimerWhenFromNumberStringShouldMsToNanosValue() {
		MeterValue meterValue = MeterValue.valueOf("123");
		assertThat(meterValue.getValue(Type.TIMER)).isEqualTo(123000000);
	}

	@Test
	void getValueForTimerWhenFromDurationStringShouldReturnDurationNanos() {
		MeterValue meterValue = MeterValue.valueOf("123ms");
		assertThat(meterValue.getValue(Type.TIMER)).isEqualTo(123000000);
	}

	@Test
	void getValueForOthersShouldReturnNull() {
		MeterValue meterValue = MeterValue.valueOf("123");
		assertThat(meterValue.getValue(Type.COUNTER)).isNull();
		assertThat(meterValue.getValue(Type.GAUGE)).isNull();
		assertThat(meterValue.getValue(Type.LONG_TASK_TIMER)).isNull();
		assertThat(meterValue.getValue(Type.OTHER)).isNull();
	}

	@Test
	void valueOfShouldWorkInBinder() {
		MockEnvironment environment = new MockEnvironment();
		TestPropertyValues.of("duration=10ms", "number=20.42").applyTo(environment);
		assertThat(Binder.get(environment).bind("duration", Bindable.of(MeterValue.class)).get().getValue(Type.TIMER))
				.isEqualTo(10000000);
		assertThat(Binder.get(environment).bind("number", Bindable.of(MeterValue.class)).get()
				.getValue(Type.DISTRIBUTION_SUMMARY)).isEqualTo(20.42);
	}

}
