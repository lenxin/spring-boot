package org.springframework.boot.actuate.autoconfigure.availability;

import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import org.springframework.boot.actuate.health.HealthEndpointGroups;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

/**
 * Tests for {@link AvailabilityProbesHealthEndpointGroupsPostProcessor}.
 *

 */
class AvailabilityProbesHealthEndpointGroupsPostProcessorTests {

	private AvailabilityProbesHealthEndpointGroupsPostProcessor postProcessor = new AvailabilityProbesHealthEndpointGroupsPostProcessor();

	@Test
	void postProcessHealthEndpointGroupsWhenGroupsAlreadyContainedReturnsOriginal() {
		HealthEndpointGroups groups = mock(HealthEndpointGroups.class);
		Set<String> names = new LinkedHashSet<>();
		names.add("test");
		names.add("readiness");
		names.add("liveness");
		given(groups.getNames()).willReturn(names);
		assertThat(this.postProcessor.postProcessHealthEndpointGroups(groups)).isSameAs(groups);
	}

	@Test
	void postProcessHealthEndpointGroupsWhenGroupContainsOneReturnsPostProcessed() {
		HealthEndpointGroups groups = mock(HealthEndpointGroups.class);
		Set<String> names = new LinkedHashSet<>();
		names.add("test");
		names.add("readiness");
		given(groups.getNames()).willReturn(names);
		assertThat(this.postProcessor.postProcessHealthEndpointGroups(groups))
				.isInstanceOf(AvailabilityProbesHealthEndpointGroups.class);
	}

	@Test
	void postProcessHealthEndpointGroupsWhenGroupsContainsNoneReturnsProcessed() {
		HealthEndpointGroups groups = mock(HealthEndpointGroups.class);
		Set<String> names = new LinkedHashSet<>();
		names.add("test");
		names.add("spring");
		names.add("boot");
		given(groups.getNames()).willReturn(names);
		assertThat(this.postProcessor.postProcessHealthEndpointGroups(groups))
				.isInstanceOf(AvailabilityProbesHealthEndpointGroups.class);

	}

}
