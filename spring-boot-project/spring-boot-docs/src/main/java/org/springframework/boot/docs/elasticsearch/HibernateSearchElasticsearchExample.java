package org.springframework.boot.docs.elasticsearch;

import javax.persistence.EntityManagerFactory;

import org.springframework.boot.autoconfigure.data.jpa.EntityManagerFactoryDependsOnPostProcessor;
import org.springframework.stereotype.Component;

/**
 * Example configuration for configuring Hibernate to depend on Elasticsearch so that
 * Hibernate Search can use Elasticsearch as its index manager.
 *

 */
public class HibernateSearchElasticsearchExample {

	// tag::configuration[]
	/**
	 * {@link EntityManagerFactoryDependsOnPostProcessor} that ensures that
	 * {@link EntityManagerFactory} beans depend on the {@code elasticsearchClient} bean.
	 */
	@Component
	static class ElasticsearchEntityManagerFactoryDependsOnPostProcessor
			extends EntityManagerFactoryDependsOnPostProcessor {

		ElasticsearchEntityManagerFactoryDependsOnPostProcessor() {
			super("elasticsearchClient");
		}

	}
	// end::configuration[]

}
