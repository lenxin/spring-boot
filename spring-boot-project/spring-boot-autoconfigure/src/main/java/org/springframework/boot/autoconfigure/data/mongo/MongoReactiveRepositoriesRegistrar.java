package org.springframework.boot.autoconfigure.data.mongo;

import java.lang.annotation.Annotation;

import org.springframework.boot.autoconfigure.data.AbstractRepositoryConfigurationSourceSupport;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.data.mongodb.repository.config.ReactiveMongoRepositoryConfigurationExtension;
import org.springframework.data.repository.config.RepositoryConfigurationExtension;

/**
 * {@link ImportBeanDefinitionRegistrar} used to auto-configure Spring Data Mongo Reactive
 * Repositories.
 *

 */
class MongoReactiveRepositoriesRegistrar extends AbstractRepositoryConfigurationSourceSupport {

	@Override
	protected Class<? extends Annotation> getAnnotation() {
		return EnableReactiveMongoRepositories.class;
	}

	@Override
	protected Class<?> getConfiguration() {
		return EnableReactiveMongoRepositoriesConfiguration.class;
	}

	@Override
	protected RepositoryConfigurationExtension getRepositoryConfigurationExtension() {
		return new ReactiveMongoRepositoryConfigurationExtension();
	}

	@EnableReactiveMongoRepositories
	private static class EnableReactiveMongoRepositoriesConfiguration {

	}

}
