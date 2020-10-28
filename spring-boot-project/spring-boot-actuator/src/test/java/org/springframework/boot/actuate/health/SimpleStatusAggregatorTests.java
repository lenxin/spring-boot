package org.springframework.boot.actuate.health;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link SimpleStatusAggregator}
 *


 */
class SimpleStatusAggregatorTests {

	@Test
	void getAggregateStatusWhenUsingDefaultInstance() {
		StatusAggregator aggregator = StatusAggregator.getDefault();
		Status status = aggregator.getAggregateStatus(Status.DOWN, Status.UP, Status.UNKNOWN, Status.OUT_OF_SERVICE);
		assertThat(status).isEqualTo(Status.DOWN);
	}

	@Test
	void getAggregateStatusWhenUsingNewDefaultOrder() {
		SimpleStatusAggregator aggregator = new SimpleStatusAggregator();
		Status status = aggregator.getAggregateStatus(Status.DOWN, Status.UP, Status.UNKNOWN, Status.OUT_OF_SERVICE);
		assertThat(status).isEqualTo(Status.DOWN);
	}

	@Test
	void getAggregateStatusWhenUsingCustomOrder() {
		SimpleStatusAggregator aggregator = new SimpleStatusAggregator(Status.UNKNOWN, Status.UP, Status.OUT_OF_SERVICE,
				Status.DOWN);
		Status status = aggregator.getAggregateStatus(Status.DOWN, Status.UP, Status.UNKNOWN, Status.OUT_OF_SERVICE);
		assertThat(status).isEqualTo(Status.UNKNOWN);
	}

	@Test
	void getAggregateStatusWhenHasCustomStatusAndUsingDefaultOrder() {
		SimpleStatusAggregator aggregator = new SimpleStatusAggregator();
		Status status = aggregator.getAggregateStatus(Status.DOWN, Status.UP, Status.UNKNOWN, Status.OUT_OF_SERVICE,
				new Status("CUSTOM"));
		assertThat(status).isEqualTo(Status.DOWN);
	}

	@Test
	void getAggregateStatusWhenHasCustomStatusAndUsingCustomOrder() {
		SimpleStatusAggregator aggregator = new SimpleStatusAggregator("DOWN", "OUT_OF_SERVICE", "UP", "UNKNOWN",
				"CUSTOM");
		Status status = aggregator.getAggregateStatus(Status.DOWN, Status.UP, Status.UNKNOWN, Status.OUT_OF_SERVICE,
				new Status("CUSTOM"));
		assertThat(status).isEqualTo(Status.DOWN);
	}

	@Test
	void createWithNonUniformCodes() {
		SimpleStatusAggregator aggregator = new SimpleStatusAggregator("out-of-service", "up");
		Status status = aggregator.getAggregateStatus(Status.UP, Status.OUT_OF_SERVICE);
		assertThat(status).isEqualTo(Status.OUT_OF_SERVICE);
	}

}
