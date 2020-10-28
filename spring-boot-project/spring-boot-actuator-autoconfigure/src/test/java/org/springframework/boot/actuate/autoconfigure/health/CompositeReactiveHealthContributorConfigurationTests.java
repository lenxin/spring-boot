package org.springframework.boot.actuate.autoconfigure.health;

import reactor.core.publisher.Mono;

import org.springframework.boot.actuate.autoconfigure.health.CompositeReactiveHealthContributorConfigurationTests.TestReactiveHealthIndicator;
import org.springframework.boot.actuate.health.AbstractReactiveHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Health.Builder;
import org.springframework.boot.actuate.health.ReactiveHealthContributor;

/**
 * Tests for {@link CompositeReactiveHealthContributorConfiguration}.
 *

 */
class CompositeReactiveHealthContributorConfigurationTests extends
		AbstractCompositeHealthContributorConfigurationTests<ReactiveHealthContributor, TestReactiveHealthIndicator> {

	@Override
	protected AbstractCompositeHealthContributorConfiguration<ReactiveHealthContributor, TestReactiveHealthIndicator, TestBean> newComposite() {
		return new TestCompositeReactiveHealthContributorConfiguration();
	}

	static class TestCompositeReactiveHealthContributorConfiguration
			extends CompositeReactiveHealthContributorConfiguration<TestReactiveHealthIndicator, TestBean> {

	}

	static class TestReactiveHealthIndicator extends AbstractReactiveHealthIndicator {

		TestReactiveHealthIndicator(TestBean testBean) {
		}

		@Override
		protected Mono<Health> doHealthCheck(Builder builder) {
			return Mono.just(builder.up().build());
		}

	}

}
