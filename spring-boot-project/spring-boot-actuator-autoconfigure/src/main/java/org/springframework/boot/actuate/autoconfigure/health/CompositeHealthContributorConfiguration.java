package org.springframework.boot.actuate.autoconfigure.health;

import java.util.Map;

import org.springframework.boot.actuate.health.CompositeHealthContributor;
import org.springframework.boot.actuate.health.HealthContributor;
import org.springframework.boot.actuate.health.HealthIndicator;

/**
 * Base class for health contributor configurations that can combine source beans into a
 * composite.
 *
 * @param <I> the health indicator type
 * @param <B> the bean type


 * @since 2.2.0
 */
public abstract class CompositeHealthContributorConfiguration<I extends HealthIndicator, B>
		extends AbstractCompositeHealthContributorConfiguration<HealthContributor, I, B> {

	@Override
	protected final HealthContributor createComposite(Map<String, B> beans) {
		return CompositeHealthContributor.fromMap(beans, this::createIndicator);
	}

}
