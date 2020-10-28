package org.springframework.boot.actuate.health;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link HealthIndicator}.
 *

 */
class HealthIndicatorTests {

	private final HealthIndicator indicator = () -> Health.up().withDetail("spring", "boot").build();

	@Test
	void getHealthWhenIncludeDetailsIsTrueReturnsHealthWithDetails() {
		Health health = this.indicator.getHealth(true);
		assertThat(health.getStatus()).isEqualTo(Status.UP);
		assertThat(health.getDetails()).containsEntry("spring", "boot");
	}

	@Test
	void getHealthWhenIncludeDetailsIsFalseReturnsHealthWithoutDetails() {
		Health health = this.indicator.getHealth(false);
		assertThat(health.getStatus()).isEqualTo(Status.UP);
		assertThat(health.getDetails()).isEmpty();
	}

}
