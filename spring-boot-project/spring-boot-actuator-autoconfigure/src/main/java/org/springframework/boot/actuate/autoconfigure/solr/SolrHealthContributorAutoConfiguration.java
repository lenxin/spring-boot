package org.springframework.boot.actuate.autoconfigure.solr;

import java.util.Map;

import org.apache.solr.client.solrj.SolrClient;

import org.springframework.boot.actuate.autoconfigure.health.CompositeHealthContributorConfiguration;
import org.springframework.boot.actuate.autoconfigure.health.ConditionalOnEnabledHealthIndicator;
import org.springframework.boot.actuate.health.HealthContributor;
import org.springframework.boot.actuate.solr.SolrHealthIndicator;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.solr.SolrAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for {@link SolrHealthIndicator}.
 *


 * @since 2.0.0
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(SolrClient.class)
@ConditionalOnBean(SolrClient.class)
@ConditionalOnEnabledHealthIndicator("solr")
@AutoConfigureAfter(SolrAutoConfiguration.class)
public class SolrHealthContributorAutoConfiguration
		extends CompositeHealthContributorConfiguration<SolrHealthIndicator, SolrClient> {

	@Bean
	@ConditionalOnMissingBean(name = { "solrHealthIndicator", "solrHealthContributor" })
	public HealthContributor solrHealthContributor(Map<String, SolrClient> solrClients) {
		return createContributor(solrClients);
	}

}
