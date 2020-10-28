package org.springframework.boot.autoconfigure.data.neo4j;

import java.lang.annotation.Annotation;

import org.springframework.boot.autoconfigure.data.AbstractRepositoryConfigurationSourceSupport;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.repository.config.Neo4jRepositoryConfigurationExtension;
import org.springframework.data.repository.config.RepositoryConfigurationExtension;

/**
 * {@link ImportBeanDefinitionRegistrar} used to auto-configure Spring Data Neo4j
 * Repositories.
 *

 */
class Neo4jRepositoriesRegistrar extends AbstractRepositoryConfigurationSourceSupport {

	@Override
	protected Class<? extends Annotation> getAnnotation() {
		return EnableNeo4jRepositories.class;
	}

	@Override
	protected Class<?> getConfiguration() {
		return EnableNeo4jRepositoriesConfiguration.class;
	}

	@Override
	protected RepositoryConfigurationExtension getRepositoryConfigurationExtension() {
		return new Neo4jRepositoryConfigurationExtension();
	}

	@EnableNeo4jRepositories
	private static class EnableNeo4jRepositoriesConfiguration {

	}

}
