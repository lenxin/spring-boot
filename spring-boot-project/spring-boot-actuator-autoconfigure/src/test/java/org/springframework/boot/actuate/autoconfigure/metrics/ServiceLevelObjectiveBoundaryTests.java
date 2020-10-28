package org.springframework.boot.actuate.autoconfigure.metrics;

import io.micrometer.core.instrument.Meter.Type;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link ServiceLevelObjectiveBoundary}.
 *


 */
class ServiceLevelObjectiveBoundaryTests {

	@Test
	void getValueForTimerWhenFromLongShouldReturnMsToNanosValue() {
		ServiceLevelObjectiveBoundary slo = ServiceLevelObjectiveBoundary.valueOf(123L);
		assertThat(slo.getValue(Type.TIMER)).isEqualTo(123000000);
	}

	@Test
	void getValueForTimerWhenFromNumberStringShouldMsToNanosValue() {
		ServiceLevelObjectiveBoundary slo = ServiceLevelObjectiveBoundary.valueOf("123");
		assertThat(slo.getValue(Type.TIMER)).isEqualTo(123000000);
	}

	@Test
	void getValueForTimerWhenFromDurationStringShouldReturnDurationNanos() {
		ServiceLevelObjectiveBoundary slo = ServiceLevelObjectiveBoundary.valueOf("123ms");
		assertThat(slo.getValue(Type.TIMER)).isEqualTo(123000000);
	}

	@Test
	void getValueForDistributionSummaryWhenFromDoubleShouldReturnDoubleValue() {
		ServiceLevelObjectiveBoundary slo = ServiceLevelObjectiveBoundary.valueOf(123.42);
		assertThat(slo.getValue(Type.DISTRIBUTION_SUMMARY)).isEqualTo(123.42);
	}

	@Test
	void getValueForDistributionSummaryWhenFromStringShouldReturnDoubleValue() {
		ServiceLevelObjectiveBoundary slo = ServiceLevelObjectiveBoundary.valueOf("123.42");
		assertThat(slo.getValue(Type.DISTRIBUTION_SUMMARY)).isEqualTo(123.42);
	}

	@Test
	void getValueForDistributionSummaryWhenFromDurationShouldReturnNull() {
		ServiceLevelObjectiveBoundary slo = ServiceLevelObjectiveBoundary.valueOf("123ms");
		assertThat(slo.getValue(Type.DISTRIBUTION_SUMMARY)).isNull();
	}

}
