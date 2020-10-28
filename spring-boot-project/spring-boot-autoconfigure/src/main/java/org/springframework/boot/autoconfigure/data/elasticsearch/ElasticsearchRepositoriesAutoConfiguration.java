package org.springframework.boot.autoconfigure.data.elasticsearch;

import org.elasticsearch.client.Client;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.elasticsearch.repository.support.ElasticsearchRepositoryFactoryBean;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for Spring Data's Elasticsearch
 * Repositories.
 *


 * @see EnableElasticsearchRepositories
 * @since 1.1.0
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass({ Client.class, ElasticsearchRepository.class })
@ConditionalOnProperty(prefix = "spring.data.elasticsearch.repositories", name = "enabled", havingValue = "true",
		matchIfMissing = true)
@ConditionalOnMissingBean(ElasticsearchRepositoryFactoryBean.class)
@Import(ElasticsearchRepositoriesRegistrar.class)
public class ElasticsearchRepositoriesAutoConfiguration {

}
