package org.springframework.boot.actuate.autoconfigure.elasticsearch;

import org.junit.jupiter.api.Test;

import org.springframework.boot.actuate.autoconfigure.health.HealthContributorAutoConfiguration;
import org.springframework.boot.actuate.elasticsearch.ElasticsearchReactiveHealthIndicator;
import org.springframework.boot.actuate.elasticsearch.ElasticsearchRestHealthIndicator;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchDataAutoConfiguration;
import org.springframework.boot.autoconfigure.data.elasticsearch.ReactiveElasticsearchRestClientAutoConfiguration;
import org.springframework.boot.autoconfigure.elasticsearch.ElasticsearchRestClientAutoConfiguration;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link ElasticSearchReactiveHealthContributorAutoConfiguration}.
 *

 */
class ElasticsearchReactiveHealthContributorAutoConfigurationTests {

	private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
			.withConfiguration(AutoConfigurations.of(ElasticsearchDataAutoConfiguration.class,
					ReactiveElasticsearchRestClientAutoConfiguration.class,
					ElasticsearchRestClientAutoConfiguration.class,
					ElasticSearchReactiveHealthContributorAutoConfiguration.class,
					HealthContributorAutoConfiguration.class));

	@Test
	void runShouldCreateIndicator() {
		this.contextRunner.run((context) -> assertThat(context)
				.hasSingleBean(ElasticsearchReactiveHealthIndicator.class).hasBean("elasticsearchHealthContributor"));
	}

	@Test
	void runWithRegularIndicatorShouldOnlyCreateReactiveIndicator() {
		this.contextRunner
				.withConfiguration(AutoConfigurations.of(ElasticSearchRestHealthContributorAutoConfiguration.class))
				.run((context) -> assertThat(context).hasSingleBean(ElasticsearchReactiveHealthIndicator.class)
						.hasBean("elasticsearchHealthContributor")
						.doesNotHaveBean(ElasticsearchRestHealthIndicator.class));
	}

	@Test
	void runWhenDisabledShouldNotCreateIndicator() {
		this.contextRunner.withPropertyValues("management.health.elasticsearch.enabled:false")
				.run((context) -> assertThat(context).doesNotHaveBean(ElasticsearchReactiveHealthIndicator.class)
						.doesNotHaveBean("elasticsearchHealthContributor"));
	}

}
