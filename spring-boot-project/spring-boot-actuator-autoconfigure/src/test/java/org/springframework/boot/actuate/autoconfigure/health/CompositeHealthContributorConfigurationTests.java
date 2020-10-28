package org.springframework.boot.actuate.autoconfigure.health;

import org.springframework.boot.actuate.autoconfigure.health.CompositeHealthContributorConfigurationTests.TestHealthIndicator;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health.Builder;
import org.springframework.boot.actuate.health.HealthContributor;

/**
 * Tests for {@link CompositeHealthContributorConfiguration}.
 *

 */
class CompositeHealthContributorConfigurationTests
		extends AbstractCompositeHealthContributorConfigurationTests<HealthContributor, TestHealthIndicator> {

	@Override
	protected AbstractCompositeHealthContributorConfiguration<HealthContributor, TestHealthIndicator, TestBean> newComposite() {
		return new TestCompositeHealthContributorConfiguration();
	}

	static class TestCompositeHealthContributorConfiguration
			extends CompositeHealthContributorConfiguration<TestHealthIndicator, TestBean> {

	}

	static class TestHealthIndicator extends AbstractHealthIndicator {

		TestHealthIndicator(TestBean testBean) {
		}

		@Override
		protected void doHealthCheck(Builder builder) throws Exception {
			builder.up();
		}

	}

}
