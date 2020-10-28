package org.springframework.boot.actuate.autoconfigure.health;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration properties for {@link HealthEndpoint}.
 *


 * @since 2.0.0
 */
@ConfigurationProperties("management.endpoint.health")
public class HealthEndpointProperties extends HealthProperties {

	/**
	 * When to show full health details.
	 */
	private Show showDetails = Show.NEVER;

	/**
	 * Health endpoint groups.
	 */
	private Map<String, Group> group = new LinkedHashMap<>();

	@Override
	public Show getShowDetails() {
		return this.showDetails;
	}

	public void setShowDetails(Show showDetails) {
		this.showDetails = showDetails;
	}

	public Map<String, Group> getGroup() {
		return this.group;
	}

	/**
	 * A health endpoint group.
	 */
	public static class Group extends HealthProperties {

		/**
		 * Health indicator IDs that should be included or '*' for all.
		 */
		private Set<String> include;

		/**
		 * Health indicator IDs that should be excluded or '*' for all.
		 */
		private Set<String> exclude;

		/**
		 * When to show full health details. Defaults to the value of
		 * 'management.endpoint.health.show-details'.
		 */
		private Show showDetails;

		public Set<String> getInclude() {
			return this.include;
		}

		public void setInclude(Set<String> include) {
			this.include = include;
		}

		public Set<String> getExclude() {
			return this.exclude;
		}

		public void setExclude(Set<String> exclude) {
			this.exclude = exclude;
		}

		@Override
		public Show getShowDetails() {
			return this.showDetails;
		}

		public void setShowDetails(Show showDetails) {
			this.showDetails = showDetails;
		}

	}

}
