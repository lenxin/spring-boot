package org.springframework.boot.actuate.autoconfigure.redis;

import org.junit.jupiter.api.Test;

import org.springframework.boot.actuate.autoconfigure.health.HealthContributorAutoConfiguration;
import org.springframework.boot.actuate.redis.RedisHealthIndicator;
import org.springframework.boot.actuate.redis.RedisReactiveHealthIndicator;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link RedisReactiveHealthContributorAutoConfiguration}.
 *

 */
class RedisReactiveHealthContributorAutoConfigurationTests {

	private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
			.withConfiguration(AutoConfigurations.of(RedisAutoConfiguration.class,
					RedisReactiveHealthContributorAutoConfiguration.class, HealthContributorAutoConfiguration.class));

	@Test
	void runShouldCreateIndicator() {
		this.contextRunner.run((context) -> assertThat(context).hasSingleBean(RedisReactiveHealthIndicator.class)
				.hasBean("redisHealthContributor"));
	}

	@Test
	void runWithRegularIndicatorShouldOnlyCreateReactiveIndicator() {
		this.contextRunner.withConfiguration(AutoConfigurations.of(RedisHealthContributorAutoConfiguration.class))
				.run((context) -> assertThat(context).hasSingleBean(RedisReactiveHealthIndicator.class)
						.hasBean("redisHealthContributor").doesNotHaveBean(RedisHealthIndicator.class));
	}

	@Test
	void runWhenDisabledShouldNotCreateIndicator() {
		this.contextRunner.withPropertyValues("management.health.redis.enabled:false")
				.run((context) -> assertThat(context).doesNotHaveBean(RedisReactiveHealthIndicator.class)
						.doesNotHaveBean("redisHealthContributor"));
	}

}
