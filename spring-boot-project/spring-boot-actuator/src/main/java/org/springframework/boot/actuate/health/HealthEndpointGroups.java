package org.springframework.boot.actuate.health;

import java.util.Map;
import java.util.Set;

import org.springframework.util.Assert;

/**
 * A collection of {@link HealthEndpointGroup groups} for use with a health endpoint.
 *

 * @since 2.2.0
 */
public interface HealthEndpointGroups {

	/**
	 * Return the primary group used by the endpoint.
	 * @return the primary group (never {@code null})
	 */
	HealthEndpointGroup getPrimary();

	/**
	 * Return the names of any additional groups.
	 * @return the additional group names
	 */
	Set<String> getNames();

	/**
	 * Return the group with the specified name or {@code null} if the name is not known.
	 * @param name the name of the group
	 * @return the {@link HealthEndpointGroup} or {@code null}
	 */
	HealthEndpointGroup get(String name);

	/**
	 * Factory method to create a {@link HealthEndpointGroups} instance.
	 * @param primary the primary group
	 * @param additional the additional groups
	 * @return a new {@link HealthEndpointGroups} instance
	 */
	static HealthEndpointGroups of(HealthEndpointGroup primary, Map<String, HealthEndpointGroup> additional) {
		Assert.notNull(primary, "Primary must not be null");
		Assert.notNull(additional, "Additional must not be null");
		return new HealthEndpointGroups() {

			@Override
			public HealthEndpointGroup getPrimary() {
				return primary;
			}

			@Override
			public Set<String> getNames() {
				return additional.keySet();
			}

			@Override
			public HealthEndpointGroup get(String name) {
				return additional.get(name);
			}

		};
	}

}
