package org.springframework.boot.actuate.autoconfigure.availability;

import org.springframework.boot.actuate.health.HealthEndpointGroups;
import org.springframework.boot.actuate.health.HealthEndpointGroupsPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

/**
 * {@link HealthEndpointGroupsPostProcessor} to add
 * {@link AvailabilityProbesHealthEndpointGroups}.
 *

 */
@Order(Ordered.LOWEST_PRECEDENCE)
class AvailabilityProbesHealthEndpointGroupsPostProcessor implements HealthEndpointGroupsPostProcessor {

	@Override
	public HealthEndpointGroups postProcessHealthEndpointGroups(HealthEndpointGroups groups) {
		if (AvailabilityProbesHealthEndpointGroups.containsAllProbeGroups(groups)) {
			return groups;
		}
		return new AvailabilityProbesHealthEndpointGroups(groups);
	}

}
