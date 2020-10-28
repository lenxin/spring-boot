package org.springframework.boot.autoconfigure.data.elasticsearch;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.elasticsearch.ElasticsearchRestClientAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.elasticsearch.repository.config.EnableReactiveElasticsearchRepositories;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for Spring Data's Elasticsearch
 * support.
 *



 * @see EnableElasticsearchRepositories
 * @see EnableReactiveElasticsearchRepositories
 * @since 1.1.0
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass({ ElasticsearchRestTemplate.class })
@AutoConfigureAfter({ ElasticsearchRestClientAutoConfiguration.class,
		ReactiveElasticsearchRestClientAutoConfiguration.class })
@Import({ ElasticsearchDataConfiguration.BaseConfiguration.class,
		ElasticsearchDataConfiguration.RestClientConfiguration.class,
		ElasticsearchDataConfiguration.ReactiveRestClientConfiguration.class })
public class ElasticsearchDataAutoConfiguration {

}
