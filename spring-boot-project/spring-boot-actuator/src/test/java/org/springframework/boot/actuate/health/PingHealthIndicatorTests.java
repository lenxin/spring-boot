package org.springframework.boot.actuate.health;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link PingHealthIndicator}.
 *

 */
class PingHealthIndicatorTests {

	@Test
	void indicatesUp() {
		PingHealthIndicator healthIndicator = new PingHealthIndicator();
		assertThat(healthIndicator.health().getStatus()).isEqualTo(Status.UP);
	}

}
