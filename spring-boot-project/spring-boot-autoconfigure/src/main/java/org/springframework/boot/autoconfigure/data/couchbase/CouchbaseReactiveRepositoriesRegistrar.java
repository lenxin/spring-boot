package org.springframework.boot.autoconfigure.data.couchbase;

import java.lang.annotation.Annotation;

import org.springframework.boot.autoconfigure.data.AbstractRepositoryConfigurationSourceSupport;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.data.couchbase.repository.config.EnableReactiveCouchbaseRepositories;
import org.springframework.data.couchbase.repository.config.ReactiveCouchbaseRepositoryConfigurationExtension;
import org.springframework.data.repository.config.RepositoryConfigurationExtension;

/**
 * {@link ImportBeanDefinitionRegistrar} used to auto-configure Spring Data Couchbase
 * Reactive Repositories.
 *

 */
class CouchbaseReactiveRepositoriesRegistrar extends AbstractRepositoryConfigurationSourceSupport {

	@Override
	protected Class<? extends Annotation> getAnnotation() {
		return EnableReactiveCouchbaseRepositories.class;
	}

	@Override
	protected Class<?> getConfiguration() {
		return EnableReactiveCouchbaseRepositoriesConfiguration.class;
	}

	@Override
	protected RepositoryConfigurationExtension getRepositoryConfigurationExtension() {
		return new ReactiveCouchbaseRepositoryConfigurationExtension();
	}

	@EnableReactiveCouchbaseRepositories
	private static class EnableReactiveCouchbaseRepositoriesConfiguration {

	}

}
