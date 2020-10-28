package org.springframework.boot.actuate.autoconfigure.mongo;

import org.junit.jupiter.api.Test;

import org.springframework.boot.actuate.autoconfigure.health.HealthContributorAutoConfiguration;
import org.springframework.boot.actuate.mongo.MongoHealthIndicator;
import org.springframework.boot.actuate.mongo.MongoReactiveHealthIndicator;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoReactiveDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoReactiveAutoConfiguration;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link MongoReactiveHealthContributorAutoConfiguration}.
 *

 */
class MongoReactiveHealthContributorAutoConfigurationTests {

	private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
			.withConfiguration(AutoConfigurations.of(MongoAutoConfiguration.class, MongoDataAutoConfiguration.class,
					MongoReactiveAutoConfiguration.class, MongoReactiveDataAutoConfiguration.class,
					MongoReactiveHealthContributorAutoConfiguration.class, HealthContributorAutoConfiguration.class));

	@Test
	void runShouldCreateIndicator() {
		this.contextRunner.run((context) -> assertThat(context).hasSingleBean(MongoReactiveHealthIndicator.class)
				.hasBean("mongoHealthContributor"));
	}

	@Test
	void runWithRegularIndicatorShouldOnlyCreateReactiveIndicator() {
		this.contextRunner.withConfiguration(AutoConfigurations.of(MongoHealthContributorAutoConfiguration.class))
				.run((context) -> assertThat(context).hasSingleBean(MongoReactiveHealthIndicator.class)
						.hasBean("mongoHealthContributor").doesNotHaveBean(MongoHealthIndicator.class));
	}

	@Test
	void runWhenDisabledShouldNotCreateIndicator() {
		this.contextRunner.withPropertyValues("management.health.mongo.enabled:false")
				.run((context) -> assertThat(context).doesNotHaveBean(MongoReactiveHealthIndicator.class)
						.doesNotHaveBean("mongoHealthContributor"));
	}

}
