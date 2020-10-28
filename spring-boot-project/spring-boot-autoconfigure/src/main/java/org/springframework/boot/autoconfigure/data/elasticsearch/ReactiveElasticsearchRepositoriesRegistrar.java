package org.springframework.boot.autoconfigure.data.elasticsearch;

import java.lang.annotation.Annotation;

import org.springframework.boot.autoconfigure.data.AbstractRepositoryConfigurationSourceSupport;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.data.elasticsearch.repository.config.EnableReactiveElasticsearchRepositories;
import org.springframework.data.elasticsearch.repository.config.ReactiveElasticsearchRepositoryConfigurationExtension;
import org.springframework.data.repository.config.RepositoryConfigurationExtension;

/**
 * {@link ImportBeanDefinitionRegistrar} used to auto-configure Spring Data Elasticsearch
 * Reactive Repositories.
 *

 */
class ReactiveElasticsearchRepositoriesRegistrar extends AbstractRepositoryConfigurationSourceSupport {

	@Override
	protected Class<? extends Annotation> getAnnotation() {
		return EnableReactiveElasticsearchRepositories.class;
	}

	@Override
	protected Class<?> getConfiguration() {
		return EnableElasticsearchRepositoriesConfiguration.class;
	}

	@Override
	protected RepositoryConfigurationExtension getRepositoryConfigurationExtension() {
		return new ReactiveElasticsearchRepositoryConfigurationExtension();
	}

	@EnableReactiveElasticsearchRepositories
	private static class EnableElasticsearchRepositoriesConfiguration {

	}

}
