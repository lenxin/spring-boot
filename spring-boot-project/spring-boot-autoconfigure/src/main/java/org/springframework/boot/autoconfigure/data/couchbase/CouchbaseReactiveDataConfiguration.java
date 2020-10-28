package org.springframework.boot.autoconfigure.data.couchbase;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.couchbase.CouchbaseClientFactory;
import org.springframework.data.couchbase.config.BeanNames;
import org.springframework.data.couchbase.core.ReactiveCouchbaseTemplate;
import org.springframework.data.couchbase.core.convert.MappingCouchbaseConverter;
import org.springframework.data.couchbase.repository.config.ReactiveRepositoryOperationsMapping;

/**
 * Configuration for Spring Data's couchbase reactive support.
 *

 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnSingleCandidate(CouchbaseClientFactory.class)
class CouchbaseReactiveDataConfiguration {

	@Bean(name = BeanNames.REACTIVE_COUCHBASE_TEMPLATE)
	@ConditionalOnMissingBean(name = BeanNames.REACTIVE_COUCHBASE_TEMPLATE)
	ReactiveCouchbaseTemplate reactiveCouchbaseTemplate(CouchbaseClientFactory couchbaseClientFactory,
			MappingCouchbaseConverter mappingCouchbaseConverter) {
		return new ReactiveCouchbaseTemplate(couchbaseClientFactory, mappingCouchbaseConverter);
	}

	@Bean(name = BeanNames.REACTIVE_COUCHBASE_OPERATIONS_MAPPING)
	@ConditionalOnMissingBean(name = BeanNames.REACTIVE_COUCHBASE_OPERATIONS_MAPPING)
	ReactiveRepositoryOperationsMapping reactiveCouchbaseRepositoryOperationsMapping(
			ReactiveCouchbaseTemplate reactiveCouchbaseTemplate) {
		return new ReactiveRepositoryOperationsMapping(reactiveCouchbaseTemplate);
	}

}
