package org.springframework.boot.actuate.health;

import reactor.core.publisher.Mono;

/**
 * Strategy interface used to contribute {@link Health} to the results returned from the
 * reactive variant of the {@link HealthEndpoint}.
 * <p>
 * This is non blocking contract that is meant to be used in a reactive application. See
 * {@link HealthIndicator} for the traditional contract.
 *

 * @since 2.0.0
 * @see HealthIndicator
 */
@FunctionalInterface
public interface ReactiveHealthIndicator extends ReactiveHealthContributor {

	/**
	 * Provide the indicator of health.
	 * @param includeDetails if details should be included or removed
	 * @return a {@link Mono} that provides the {@link Health}
	 * @since 2.2.0
	 */
	default Mono<Health> getHealth(boolean includeDetails) {
		Mono<Health> health = health();
		return includeDetails ? health : health.map(Health::withoutDetails);
	}

	/**
	 * Provide the indicator of health.
	 * @return a {@link Mono} that provides the {@link Health}
	 */
	Mono<Health> health();

}
