package org.springframework.boot.docs.neo4j;

import org.neo4j.driver.Driver;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.core.ReactiveDatabaseSelectionProvider;
import org.springframework.data.neo4j.core.transaction.ReactiveNeo4jTransactionManager;
import org.springframework.transaction.ReactiveTransactionManager;

/**
 * Example to show user-defined registration of a {@link ReactiveTransactionManager}.
 *

 */
@Configuration(proxyBeanMethods = false)
public class Neo4jReactiveTransactionManagerExample {

	// tag::configuration[]
	@Bean
	public ReactiveNeo4jTransactionManager reactiveTransactionManager(Driver driver,
			ReactiveDatabaseSelectionProvider databaseNameProvider) {
		return new ReactiveNeo4jTransactionManager(driver, databaseNameProvider);
	}
	// end::configuration[]

}
