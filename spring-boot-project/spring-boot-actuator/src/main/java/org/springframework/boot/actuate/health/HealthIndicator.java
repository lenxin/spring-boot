package org.springframework.boot.actuate.health;

/**
 * Strategy interface used to contribute {@link Health} to the results returned from the
 * {@link HealthEndpoint}.
 *


 * @since 1.0.0
 */
@FunctionalInterface
public interface HealthIndicator extends HealthContributor {

	/**
	 * Return an indication of health.
	 * @param includeDetails if details should be included or removed
	 * @return the health
	 * @since 2.2.0
	 */
	default Health getHealth(boolean includeDetails) {
		Health health = health();
		return includeDetails ? health : health.withoutDetails();
	}

	/**
	 * Return an indication of health.
	 * @return the health
	 */
	Health health();

}
