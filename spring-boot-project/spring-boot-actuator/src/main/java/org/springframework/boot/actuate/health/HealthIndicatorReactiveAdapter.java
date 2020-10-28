package org.springframework.boot.actuate.health;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import org.springframework.util.Assert;

/**
 * Adapts a {@link HealthIndicator} to a {@link ReactiveHealthIndicator} so that it can be
 * safely invoked in a reactive environment.
 *

 */
class HealthIndicatorReactiveAdapter implements ReactiveHealthIndicator {

	private final HealthIndicator delegate;

	HealthIndicatorReactiveAdapter(HealthIndicator delegate) {
		Assert.notNull(delegate, "Delegate must not be null");
		this.delegate = delegate;
	}

	@Override
	public Mono<Health> health() {
		return Mono.fromCallable(this.delegate::health).subscribeOn(Schedulers.boundedElastic());
	}

}
