package org.springframework.boot.actuate.autoconfigure.elasticsearch;

import java.util.Map;

import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import org.springframework.boot.actuate.autoconfigure.health.CompositeHealthContributorConfiguration;
import org.springframework.boot.actuate.autoconfigure.health.ConditionalOnEnabledHealthIndicator;
import org.springframework.boot.actuate.elasticsearch.ElasticsearchRestHealthIndicator;
import org.springframework.boot.actuate.health.HealthContributor;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.elasticsearch.ElasticsearchRestClientAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for
 * {@link ElasticsearchRestHealthIndicator} using the {@link RestClient}.
 *

 * @since 2.1.1
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(RestHighLevelClient.class)
@ConditionalOnBean(RestHighLevelClient.class)
@ConditionalOnEnabledHealthIndicator("elasticsearch")
@AutoConfigureAfter(ElasticsearchRestClientAutoConfiguration.class)
public class ElasticSearchRestHealthContributorAutoConfiguration
		extends CompositeHealthContributorConfiguration<ElasticsearchRestHealthIndicator, RestHighLevelClient> {

	@Bean
	@ConditionalOnMissingBean(name = { "elasticsearchHealthIndicator", "elasticsearchHealthContributor" })
	public HealthContributor elasticsearchHealthContributor(Map<String, RestHighLevelClient> clients) {
		return createContributor(clients);
	}

}
