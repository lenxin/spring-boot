package org.springframework.boot.actuate.autoconfigure.health;

import java.util.Map;

import org.springframework.boot.actuate.health.CompositeReactiveHealthContributor;
import org.springframework.boot.actuate.health.ReactiveHealthContributor;
import org.springframework.boot.actuate.health.ReactiveHealthIndicator;

/**
 * Base class for health contributor configurations that can combine source beans into a
 * composite.
 *
 * @param <I> the health indicator type
 * @param <B> the bean type


 * @since 2.2.0
 */
public abstract class CompositeReactiveHealthContributorConfiguration<I extends ReactiveHealthIndicator, B>
		extends AbstractCompositeHealthContributorConfiguration<ReactiveHealthContributor, I, B> {

	@Override
	protected final ReactiveHealthContributor createComposite(Map<String, B> beans) {
		return CompositeReactiveHealthContributor.fromMap(beans, this::createIndicator);
	}

}
