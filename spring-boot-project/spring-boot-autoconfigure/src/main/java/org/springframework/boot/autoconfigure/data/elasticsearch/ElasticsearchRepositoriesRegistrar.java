package org.springframework.boot.autoconfigure.data.elasticsearch;

import java.lang.annotation.Annotation;

import org.springframework.boot.autoconfigure.data.AbstractRepositoryConfigurationSourceSupport;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.data.elasticsearch.repository.config.ElasticsearchRepositoryConfigExtension;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.repository.config.RepositoryConfigurationExtension;

/**
 * {@link ImportBeanDefinitionRegistrar} used to auto-configure Spring Data Elasticsearch
 * Repositories.
 *


 */
class ElasticsearchRepositoriesRegistrar extends AbstractRepositoryConfigurationSourceSupport {

	@Override
	protected Class<? extends Annotation> getAnnotation() {
		return EnableElasticsearchRepositories.class;
	}

	@Override
	protected Class<?> getConfiguration() {
		return EnableElasticsearchRepositoriesConfiguration.class;
	}

	@Override
	protected RepositoryConfigurationExtension getRepositoryConfigurationExtension() {
		return new ElasticsearchRepositoryConfigExtension();
	}

	@EnableElasticsearchRepositories
	private static class EnableElasticsearchRepositoriesConfiguration {

	}

}
