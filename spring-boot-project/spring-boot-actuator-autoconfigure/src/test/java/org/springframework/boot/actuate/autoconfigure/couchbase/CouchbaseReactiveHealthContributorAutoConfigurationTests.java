package org.springframework.boot.actuate.autoconfigure.couchbase;

import com.couchbase.client.java.Cluster;
import org.junit.jupiter.api.Test;

import org.springframework.boot.actuate.autoconfigure.health.HealthContributorAutoConfiguration;
import org.springframework.boot.actuate.couchbase.CouchbaseHealthIndicator;
import org.springframework.boot.actuate.couchbase.CouchbaseReactiveHealthIndicator;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

/**
 * Tests for {@link CouchbaseReactiveHealthContributorAutoConfiguration}.
 *

 */
class CouchbaseReactiveHealthContributorAutoConfigurationTests {

	private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
			.withBean(Cluster.class, () -> mock(Cluster.class))
			.withConfiguration(AutoConfigurations.of(CouchbaseReactiveHealthContributorAutoConfiguration.class,
					HealthContributorAutoConfiguration.class));

	@Test
	void runShouldCreateIndicator() {
		this.contextRunner.run((context) -> assertThat(context).hasSingleBean(CouchbaseReactiveHealthIndicator.class)
				.hasBean("couchbaseHealthContributor"));
	}

	@Test
	void runWithRegularIndicatorShouldOnlyCreateReactiveIndicator() {
		this.contextRunner.withConfiguration(AutoConfigurations.of(CouchbaseHealthContributorAutoConfiguration.class))
				.run((context) -> assertThat(context).hasSingleBean(CouchbaseReactiveHealthIndicator.class)
						.hasBean("couchbaseHealthContributor").doesNotHaveBean(CouchbaseHealthIndicator.class));
	}

	@Test
	void runWhenDisabledShouldNotCreateIndicator() {
		this.contextRunner.withPropertyValues("management.health.couchbase.enabled:false")
				.run((context) -> assertThat(context).doesNotHaveBean(CouchbaseReactiveHealthIndicator.class)
						.doesNotHaveBean("couchbaseHealthContributor"));
	}

}
