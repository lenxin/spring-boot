package org.springframework.boot.actuate.autoconfigure.availability;

import org.junit.jupiter.api.Test;

import org.springframework.boot.actuate.endpoint.SecurityContext;
import org.springframework.boot.actuate.health.HttpCodeStatusMapper;
import org.springframework.boot.actuate.health.StatusAggregator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

/**
 * Tests for {@link AvailabilityProbesHealthEndpointGroup}.
 *

 */
class AvailabilityProbesHealthEndpointGroupTests {

	private AvailabilityProbesHealthEndpointGroup group = new AvailabilityProbesHealthEndpointGroup("a", "b");

	@Test
	void isMemberWhenMemberReturnsTrue() {
		assertThat(this.group.isMember("a")).isTrue();
		assertThat(this.group.isMember("b")).isTrue();
	}

	@Test
	void isMemberWhenNotMemberReturnsFalse() {
		assertThat(this.group.isMember("c")).isFalse();
	}

	@Test
	void showComponentsReturnsFalse() {
		assertThat(this.group.showComponents(mock(SecurityContext.class))).isFalse();
	}

	@Test
	void showDetailsReturnsFalse() {
		assertThat(this.group.showDetails(mock(SecurityContext.class))).isFalse();
	}

	@Test
	void getStatusAggregatorReturnsDefaultStatusAggregator() {
		assertThat(this.group.getStatusAggregator()).isEqualTo(StatusAggregator.getDefault());
	}

	@Test
	void getHttpCodeStatusMapperReturnsDefaultHttpCodeStatusMapper() {
		assertThat(this.group.getHttpCodeStatusMapper()).isEqualTo(HttpCodeStatusMapper.DEFAULT);
	}

}
