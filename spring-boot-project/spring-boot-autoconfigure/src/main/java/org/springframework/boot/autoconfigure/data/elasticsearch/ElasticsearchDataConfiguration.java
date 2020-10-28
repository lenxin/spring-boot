package org.springframework.boot.autoconfigure.data.elasticsearch;

import org.elasticsearch.action.support.IndicesOptions;
import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.client.RestHighLevelClient;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.reactive.ReactiveElasticsearchClient;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.ReactiveElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ReactiveElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.convert.ElasticsearchConverter;
import org.springframework.data.elasticsearch.core.convert.MappingElasticsearchConverter;
import org.springframework.data.elasticsearch.core.mapping.SimpleElasticsearchMappingContext;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Configuration classes for Spring Data for Elasticsearch
 * <p>
 * Those should be {@code @Import} in a regular auto-configuration class to guarantee
 * their order of execution.
 *


 */
abstract class ElasticsearchDataConfiguration {

	@Configuration(proxyBeanMethods = false)
	static class BaseConfiguration {

		@Bean
		@ConditionalOnMissingBean
		ElasticsearchConverter elasticsearchConverter(SimpleElasticsearchMappingContext mappingContext) {
			return new MappingElasticsearchConverter(mappingContext);
		}

		@Bean
		@ConditionalOnMissingBean
		SimpleElasticsearchMappingContext mappingContext() {
			return new SimpleElasticsearchMappingContext();
		}

	}

	@Configuration(proxyBeanMethods = false)
	@ConditionalOnClass(RestHighLevelClient.class)
	static class RestClientConfiguration {

		@Bean
		@ConditionalOnMissingBean(value = ElasticsearchOperations.class, name = "elasticsearchTemplate")
		@ConditionalOnBean(RestHighLevelClient.class)
		ElasticsearchRestTemplate elasticsearchTemplate(RestHighLevelClient client, ElasticsearchConverter converter) {
			return new ElasticsearchRestTemplate(client, converter);
		}

	}

	@Configuration(proxyBeanMethods = false)
	@ConditionalOnClass({ WebClient.class, ReactiveElasticsearchOperations.class })
	static class ReactiveRestClientConfiguration {

		@Bean
		@ConditionalOnMissingBean(value = ReactiveElasticsearchOperations.class, name = "reactiveElasticsearchTemplate")
		@ConditionalOnBean(ReactiveElasticsearchClient.class)
		ReactiveElasticsearchTemplate reactiveElasticsearchTemplate(ReactiveElasticsearchClient client,
				ElasticsearchConverter converter) {
			ReactiveElasticsearchTemplate template = new ReactiveElasticsearchTemplate(client, converter);
			template.setIndicesOptions(IndicesOptions.strictExpandOpenAndForbidClosed());
			template.setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE);
			return template;
		}

	}

}
