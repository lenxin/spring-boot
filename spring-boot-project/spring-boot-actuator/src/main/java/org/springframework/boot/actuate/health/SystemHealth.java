package org.springframework.boot.actuate.health;

import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import org.springframework.boot.actuate.endpoint.http.ApiVersion;

/**
 * A {@link HealthComponent} that represents the overall system health and the available
 * groups.
 *

 * @since 2.2.0
 */
public final class SystemHealth extends CompositeHealth {

	private final Set<String> groups;

	SystemHealth(ApiVersion apiVersion, Status status, Map<String, HealthComponent> instances, Set<String> groups) {
		super(apiVersion, status, instances);
		this.groups = (groups != null) ? new TreeSet<>(groups) : null;
	}

	@JsonInclude(Include.NON_EMPTY)
	public Set<String> getGroups() {
		return this.groups;
	}

}
