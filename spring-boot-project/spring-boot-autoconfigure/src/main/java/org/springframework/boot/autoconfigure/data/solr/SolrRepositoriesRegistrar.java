package org.springframework.boot.autoconfigure.data.solr;

import java.lang.annotation.Annotation;

import org.springframework.boot.autoconfigure.data.AbstractRepositoryConfigurationSourceSupport;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.data.repository.config.RepositoryConfigurationExtension;
import org.springframework.data.solr.repository.config.EnableSolrRepositories;
import org.springframework.data.solr.repository.config.SolrRepositoryConfigExtension;

/**
 * {@link ImportBeanDefinitionRegistrar} used to auto-configure Spring Data Solr
 * repositories.
 *

 */
class SolrRepositoriesRegistrar extends AbstractRepositoryConfigurationSourceSupport {

	@Override
	protected Class<? extends Annotation> getAnnotation() {
		return EnableSolrRepositories.class;
	}

	@Override
	protected Class<?> getConfiguration() {
		return EnableSolrRepositoriesConfiguration.class;
	}

	@Override
	protected RepositoryConfigurationExtension getRepositoryConfigurationExtension() {
		return new SolrRepositoryConfigExtension();
	}

	@EnableSolrRepositories
	private static class EnableSolrRepositoriesConfiguration {

	}

}
