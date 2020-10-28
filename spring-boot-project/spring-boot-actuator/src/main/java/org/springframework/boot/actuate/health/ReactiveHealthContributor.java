package org.springframework.boot.actuate.health;

import org.springframework.util.Assert;

/**
 * Tagging interface for classes that contribute to {@link HealthComponent health
 * components} to the results returned from the {@link HealthEndpoint}. A contributor must
 * be either a {@link ReactiveHealthIndicator} or a
 * {@link CompositeReactiveHealthContributor}.
 *

 * @since 2.2.0
 * @see ReactiveHealthIndicator
 * @see CompositeReactiveHealthContributor
 */
public interface ReactiveHealthContributor {

	static ReactiveHealthContributor adapt(HealthContributor healthContributor) {
		Assert.notNull(healthContributor, "HealthContributor must not be null");
		if (healthContributor instanceof HealthIndicator) {
			return new HealthIndicatorReactiveAdapter((HealthIndicator) healthContributor);
		}
		if (healthContributor instanceof CompositeHealthContributor) {
			return new CompositeHealthContributorReactiveAdapter((CompositeHealthContributor) healthContributor);
		}
		throw new IllegalStateException("Unknown HealthContributor type");
	}

}
