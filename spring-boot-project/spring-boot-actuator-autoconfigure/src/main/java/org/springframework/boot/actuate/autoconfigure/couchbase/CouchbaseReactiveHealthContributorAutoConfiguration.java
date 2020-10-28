package org.springframework.boot.actuate.autoconfigure.couchbase;

import java.util.Map;

import com.couchbase.client.java.Cluster;
import reactor.core.publisher.Flux;

import org.springframework.boot.actuate.autoconfigure.health.CompositeReactiveHealthContributorConfiguration;
import org.springframework.boot.actuate.autoconfigure.health.ConditionalOnEnabledHealthIndicator;
import org.springframework.boot.actuate.couchbase.CouchbaseReactiveHealthIndicator;
import org.springframework.boot.actuate.health.ReactiveHealthContributor;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.couchbase.CouchbaseAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for
 * {@link CouchbaseReactiveHealthIndicator}.
 *


 * @since 2.1.0
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass({ Cluster.class, Flux.class })
@ConditionalOnBean(Cluster.class)
@ConditionalOnEnabledHealthIndicator("couchbase")
@AutoConfigureAfter(CouchbaseAutoConfiguration.class)
public class CouchbaseReactiveHealthContributorAutoConfiguration
		extends CompositeReactiveHealthContributorConfiguration<CouchbaseReactiveHealthIndicator, Cluster> {

	@Bean
	@ConditionalOnMissingBean(name = { "couchbaseHealthIndicator", "couchbaseHealthContributor" })
	public ReactiveHealthContributor couchbaseHealthContributor(Map<String, Cluster> clusters) {
		return createContributor(clusters);
	}

}
