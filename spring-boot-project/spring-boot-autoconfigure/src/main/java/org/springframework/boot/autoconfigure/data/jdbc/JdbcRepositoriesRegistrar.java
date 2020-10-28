package org.springframework.boot.autoconfigure.data.jdbc;

import java.lang.annotation.Annotation;

import org.springframework.boot.autoconfigure.data.AbstractRepositoryConfigurationSourceSupport;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.data.jdbc.repository.config.JdbcRepositoryConfigExtension;
import org.springframework.data.repository.config.RepositoryConfigurationExtension;

/**
 * {@link ImportBeanDefinitionRegistrar} used to auto-configure Spring Data JDBC
 * Repositories.
 *

 */
class JdbcRepositoriesRegistrar extends AbstractRepositoryConfigurationSourceSupport {

	@Override
	protected Class<? extends Annotation> getAnnotation() {
		return EnableJdbcRepositories.class;
	}

	@Override
	protected Class<?> getConfiguration() {
		return EnableJdbcRepositoriesConfiguration.class;
	}

	@Override
	protected RepositoryConfigurationExtension getRepositoryConfigurationExtension() {
		return new JdbcRepositoryConfigExtension();
	}

	@EnableJdbcRepositories
	private static class EnableJdbcRepositoriesConfiguration {

	}

}
