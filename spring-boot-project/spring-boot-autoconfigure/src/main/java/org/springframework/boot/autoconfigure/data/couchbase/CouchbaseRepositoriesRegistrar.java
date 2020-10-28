package org.springframework.boot.autoconfigure.data.couchbase;

import java.lang.annotation.Annotation;

import org.springframework.boot.autoconfigure.data.AbstractRepositoryConfigurationSourceSupport;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.data.couchbase.repository.config.CouchbaseRepositoryConfigurationExtension;
import org.springframework.data.couchbase.repository.config.EnableCouchbaseRepositories;
import org.springframework.data.repository.config.RepositoryConfigurationExtension;

/**
 * {@link ImportBeanDefinitionRegistrar} used to auto-configure Spring Data Couchbase
 * Repositories.
 *

 */
class CouchbaseRepositoriesRegistrar extends AbstractRepositoryConfigurationSourceSupport {

	@Override
	protected Class<? extends Annotation> getAnnotation() {
		return EnableCouchbaseRepositories.class;
	}

	@Override
	protected Class<?> getConfiguration() {
		return EnableCouchbaseRepositoriesConfiguration.class;
	}

	@Override
	protected RepositoryConfigurationExtension getRepositoryConfigurationExtension() {
		return new CouchbaseRepositoryConfigurationExtension();
	}

	@EnableCouchbaseRepositories
	private static class EnableCouchbaseRepositoriesConfiguration {

	}

}
