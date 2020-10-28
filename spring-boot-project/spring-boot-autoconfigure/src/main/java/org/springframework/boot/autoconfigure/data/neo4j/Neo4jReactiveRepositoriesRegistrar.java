package org.springframework.boot.autoconfigure.data.neo4j;

import java.lang.annotation.Annotation;

import org.springframework.boot.autoconfigure.data.AbstractRepositoryConfigurationSourceSupport;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.data.neo4j.repository.config.EnableReactiveNeo4jRepositories;
import org.springframework.data.neo4j.repository.config.ReactiveNeo4jRepositoryConfigurationExtension;
import org.springframework.data.repository.config.RepositoryConfigurationExtension;

/**
 * {@link ImportBeanDefinitionRegistrar} used to auto-configure Spring Data Neo4j reactive
 * Repositories.
 *

 */
class Neo4jReactiveRepositoriesRegistrar extends AbstractRepositoryConfigurationSourceSupport {

	@Override
	protected Class<? extends Annotation> getAnnotation() {
		return EnableReactiveNeo4jRepositories.class;
	}

	@Override
	protected Class<?> getConfiguration() {
		return EnableReactiveNeo4jRepositoriesConfiguration.class;
	}

	@Override
	protected RepositoryConfigurationExtension getRepositoryConfigurationExtension() {
		return new ReactiveNeo4jRepositoryConfigurationExtension();
	}

	@EnableReactiveNeo4jRepositories
	private static class EnableReactiveNeo4jRepositoriesConfiguration {

	}

}
