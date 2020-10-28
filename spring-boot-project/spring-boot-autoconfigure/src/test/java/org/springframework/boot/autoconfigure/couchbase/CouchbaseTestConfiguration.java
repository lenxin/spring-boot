package org.springframework.boot.autoconfigure.couchbase;

import com.couchbase.client.java.Cluster;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.mockito.Mockito.mock;

/**
 * Test configuration for couchbase that mocks access.
 *

 */
@Configuration(proxyBeanMethods = false)
class CouchbaseTestConfiguration {

	private final Cluster cluster = mock(Cluster.class);

	@Bean
	Cluster couchbaseCluster() {
		return this.cluster;
	}

}
