package org.springframework.boot.actuate.availability;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.boot.actuate.health.Status;
import org.springframework.boot.availability.ApplicationAvailability;
import org.springframework.boot.availability.ReadinessState;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

/**
 * Tests for {@link ReadinessStateHealthIndicator}
 *

 */
class ReadinessStateHealthIndicatorTests {

	private ApplicationAvailability availability;

	private ReadinessStateHealthIndicator healthIndicator;

	@BeforeEach
	void setUp() {
		this.availability = mock(ApplicationAvailability.class);
		this.healthIndicator = new ReadinessStateHealthIndicator(this.availability);
	}

	@Test
	void readinessIsReady() {
		given(this.availability.getReadinessState()).willReturn(ReadinessState.ACCEPTING_TRAFFIC);
		assertThat(this.healthIndicator.health().getStatus()).isEqualTo(Status.UP);
	}

	@Test
	void readinessIsUnready() {
		given(this.availability.getReadinessState()).willReturn(ReadinessState.REFUSING_TRAFFIC);
		assertThat(this.healthIndicator.health().getStatus()).isEqualTo(Status.OUT_OF_SERVICE);
	}

}
