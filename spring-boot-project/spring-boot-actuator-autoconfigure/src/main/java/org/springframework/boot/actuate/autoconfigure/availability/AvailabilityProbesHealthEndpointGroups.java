package org.springframework.boot.actuate.autoconfigure.availability;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.boot.actuate.health.HealthEndpointGroup;
import org.springframework.boot.actuate.health.HealthEndpointGroups;
import org.springframework.util.Assert;

/**
 * {@link HealthEndpointGroups} decorator to support availability probes.
 *


 */
class AvailabilityProbesHealthEndpointGroups implements HealthEndpointGroups {

	private static final Map<String, AvailabilityProbesHealthEndpointGroup> GROUPS;
	static {
		Map<String, AvailabilityProbesHealthEndpointGroup> groups = new LinkedHashMap<>();
		groups.put("liveness", new AvailabilityProbesHealthEndpointGroup("livenessState"));
		groups.put("readiness", new AvailabilityProbesHealthEndpointGroup("readinessState"));
		GROUPS = Collections.unmodifiableMap(groups);
	}

	private final HealthEndpointGroups groups;

	private final Set<String> names;

	AvailabilityProbesHealthEndpointGroups(HealthEndpointGroups groups) {
		Assert.notNull(groups, "Groups must not be null");
		this.groups = groups;
		Set<String> names = new LinkedHashSet<>(groups.getNames());
		names.addAll(GROUPS.keySet());
		this.names = Collections.unmodifiableSet(names);
	}

	@Override
	public HealthEndpointGroup getPrimary() {
		return this.groups.getPrimary();
	}

	@Override
	public Set<String> getNames() {
		return this.names;
	}

	@Override
	public HealthEndpointGroup get(String name) {
		HealthEndpointGroup group = this.groups.get(name);
		if (group == null) {
			group = GROUPS.get(name);
		}
		return group;
	}

	static boolean containsAllProbeGroups(HealthEndpointGroups groups) {
		return groups.getNames().containsAll(GROUPS.keySet());
	}

}
