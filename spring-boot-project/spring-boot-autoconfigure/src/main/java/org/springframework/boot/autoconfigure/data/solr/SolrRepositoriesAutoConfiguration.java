package org.springframework.boot.autoconfigure.data.solr;

import org.apache.solr.client.solrj.SolrClient;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.solr.repository.SolrRepository;
import org.springframework.data.solr.repository.config.EnableSolrRepositories;
import org.springframework.data.solr.repository.config.SolrRepositoryConfigExtension;
import org.springframework.data.solr.repository.support.SolrRepositoryFactoryBean;

/**
 * Enables auto configuration for Spring Data Solr repositories.
 * <p>
 * Activates when there is no bean of type {@link SolrRepositoryFactoryBean} found in
 * context, and both {@link SolrRepository} and {@link SolrClient} can be found on
 * classpath.
 * </p>
 * If active auto configuration does the same as
 * {@link EnableSolrRepositories @EnableSolrRepositories} would do.
 *


 * @since 1.1.0
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass({ SolrClient.class, SolrRepository.class })
@ConditionalOnMissingBean({ SolrRepositoryFactoryBean.class, SolrRepositoryConfigExtension.class })
@ConditionalOnProperty(prefix = "spring.data.solr.repositories", name = "enabled", havingValue = "true",
		matchIfMissing = true)
@Import(SolrRepositoriesRegistrar.class)
public class SolrRepositoriesAutoConfiguration {

}
