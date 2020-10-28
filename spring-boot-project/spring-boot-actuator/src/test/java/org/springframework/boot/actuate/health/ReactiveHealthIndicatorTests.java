package org.springframework.boot.actuate.health;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link ReactiveHealthIndicator}.
 *

 */
class ReactiveHealthIndicatorTests {

	private final ReactiveHealthIndicator indicator = () -> Mono.just(Health.up().withDetail("spring", "boot").build());

	@Test
	void getHealthWhenIncludeDetailsIsTrueReturnsHealthWithDetails() {
		Health health = this.indicator.getHealth(true).block();
		assertThat(health.getStatus()).isEqualTo(Status.UP);
		assertThat(health.getDetails()).containsEntry("spring", "boot");
	}

	@Test
	void getHealthWhenIncludeDetailsIsFalseReturnsHealthWithoutDetails() {
		Health health = this.indicator.getHealth(false).block();
		assertThat(health.getStatus()).isEqualTo(Status.UP);
		assertThat(health.getDetails()).isEmpty();
	}

}
