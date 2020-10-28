package org.springframework.boot.autoconfigure.data.couchbase;

import com.couchbase.client.java.Cluster;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.couchbase.CouchbaseClientFactory;
import org.springframework.data.couchbase.SimpleCouchbaseClientFactory;

/**
 * Configuration for a {@link CouchbaseClientFactory}.
 *

 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnSingleCandidate(Cluster.class)
@ConditionalOnProperty("spring.data.couchbase.bucket-name")
class CouchbaseClientFactoryConfiguration {

	@Bean
	@ConditionalOnMissingBean
	CouchbaseClientFactory couchbaseClientFactory(Cluster cluster, CouchbaseDataProperties properties) {
		return new SimpleCouchbaseClientFactory(cluster, properties.getBucketName(), properties.getScopeName());
	}

}
