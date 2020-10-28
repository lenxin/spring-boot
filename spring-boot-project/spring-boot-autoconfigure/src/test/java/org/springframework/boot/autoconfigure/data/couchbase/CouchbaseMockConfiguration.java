package org.springframework.boot.autoconfigure.data.couchbase;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.couchbase.CouchbaseClientFactory;

import static org.mockito.Mockito.mock;

/**
 * Test configuration that mocks access to Couchbase.
 *

 */
@Configuration(proxyBeanMethods = false)
class CouchbaseMockConfiguration {

	@Bean
	CouchbaseClientFactory couchbaseClientFactory() {
		return mock(CouchbaseClientFactory.class);
	}

}
