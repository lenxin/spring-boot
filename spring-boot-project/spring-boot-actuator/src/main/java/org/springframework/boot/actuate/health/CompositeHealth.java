package org.springframework.boot.actuate.health;

import java.util.Map;
import java.util.TreeMap;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.boot.actuate.endpoint.http.ApiVersion;
import org.springframework.util.Assert;

/**
 * A {@link HealthComponent} that is composed of other {@link HealthComponent} instances.
 * Used to provide a unified view of related components. For example, a database health
 * indicator may be a composite containing the {@link Health} of each datasource
 * connection.
 *

 * @since 2.2.0
 */
public class CompositeHealth extends HealthComponent {

	private final Status status;

	private final Map<String, HealthComponent> components;

	private final Map<String, HealthComponent> details;

	CompositeHealth(ApiVersion apiVersion, Status status, Map<String, HealthComponent> components) {
		Assert.notNull(status, "Status must not be null");
		this.status = status;
		this.components = (apiVersion != ApiVersion.V3) ? null : sort(components);
		this.details = (apiVersion != ApiVersion.V2) ? null : sort(components);
	}

	private Map<String, HealthComponent> sort(Map<String, HealthComponent> components) {
		return (components != null) ? new TreeMap<>(components) : components;
	}

	@Override
	public Status getStatus() {
		return this.status;
	}

	@JsonInclude(Include.NON_EMPTY)
	public Map<String, HealthComponent> getComponents() {
		return this.components;
	}

	@JsonInclude(Include.NON_EMPTY)
	@JsonProperty
	Map<String, HealthComponent> getDetails() {
		return this.details;
	}

}
