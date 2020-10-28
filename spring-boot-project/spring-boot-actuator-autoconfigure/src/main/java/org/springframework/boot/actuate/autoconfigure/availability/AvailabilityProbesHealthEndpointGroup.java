package org.springframework.boot.actuate.autoconfigure.availability;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.actuate.endpoint.SecurityContext;
import org.springframework.boot.actuate.health.HealthEndpointGroup;
import org.springframework.boot.actuate.health.HttpCodeStatusMapper;
import org.springframework.boot.actuate.health.StatusAggregator;

/**
 * {@link HealthEndpointGroup} used to support availability probes.
 *


 */
class AvailabilityProbesHealthEndpointGroup implements HealthEndpointGroup {

	private final Set<String> members;

	AvailabilityProbesHealthEndpointGroup(String... members) {
		this.members = new HashSet<>(Arrays.asList(members));
	}

	@Override
	public boolean isMember(String name) {
		return this.members.contains(name);
	}

	@Override
	public boolean showComponents(SecurityContext securityContext) {
		return false;
	}

	@Override
	public boolean showDetails(SecurityContext securityContext) {
		return false;
	}

	@Override
	public StatusAggregator getStatusAggregator() {
		return StatusAggregator.getDefault();
	}

	@Override
	public HttpCodeStatusMapper getHttpCodeStatusMapper() {
		return HttpCodeStatusMapper.DEFAULT;
	}

}
