package org.springframework.boot.actuate.health;

/**
 * Tagging interface for classes that contribute to {@link HealthComponent health
 * components} to the results returned from the {@link HealthEndpoint}. A contributor must
 * be either a {@link HealthIndicator} or a {@link CompositeHealthContributor}.
 *

 * @since 2.2.0
 * @see HealthIndicator
 * @see CompositeHealthContributor
 */
public interface HealthContributor {

}
