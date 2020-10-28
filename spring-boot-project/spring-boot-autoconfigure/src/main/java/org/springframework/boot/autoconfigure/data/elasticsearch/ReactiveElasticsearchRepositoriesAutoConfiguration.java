package org.springframework.boot.autoconfigure.data.elasticsearch;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.elasticsearch.client.reactive.ReactiveElasticsearchClient;
import org.springframework.data.elasticsearch.repository.ReactiveElasticsearchRepository;
import org.springframework.data.elasticsearch.repository.config.EnableReactiveElasticsearchRepositories;
import org.springframework.data.elasticsearch.repository.support.ReactiveElasticsearchRepositoryFactoryBean;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for Spring Data's Elasticsearch
 * Reactive Repositories.
 *

 * @see EnableReactiveElasticsearchRepositories
 * @since 2.2.0
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass({ ReactiveElasticsearchClient.class, ReactiveElasticsearchRepository.class })
@ConditionalOnProperty(prefix = "spring.data.elasticsearch.repositories", name = "enabled", havingValue = "true",
		matchIfMissing = true)
@ConditionalOnMissingBean(ReactiveElasticsearchRepositoryFactoryBean.class)
@Import(ReactiveElasticsearchRepositoriesRegistrar.class)
public class ReactiveElasticsearchRepositoriesAutoConfiguration {

}
