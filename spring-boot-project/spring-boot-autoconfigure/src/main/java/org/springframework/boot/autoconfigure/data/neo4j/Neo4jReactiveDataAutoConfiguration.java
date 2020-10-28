package org.springframework.boot.autoconfigure.data.neo4j;

import org.neo4j.driver.Driver;
import reactor.core.publisher.Flux;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.neo4j.config.Neo4jDefaultReactiveCallbacksRegistrar;
import org.springframework.data.neo4j.core.ReactiveDatabaseSelectionProvider;
import org.springframework.data.neo4j.core.ReactiveNeo4jClient;
import org.springframework.data.neo4j.core.ReactiveNeo4jOperations;
import org.springframework.data.neo4j.core.ReactiveNeo4jTemplate;
import org.springframework.data.neo4j.core.mapping.Neo4jMappingContext;
import org.springframework.data.neo4j.repository.config.ReactiveNeo4jRepositoryConfigurationExtension;
import org.springframework.transaction.ReactiveTransactionManager;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for Spring Data's reactive Neo4j
 * support.
 *


 * @since 2.4.0
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass({ Driver.class, ReactiveNeo4jTemplate.class, ReactiveTransactionManager.class, Flux.class })
@ConditionalOnBean(Driver.class)
@AutoConfigureAfter(Neo4jDataAutoConfiguration.class)
@Import(Neo4jDefaultReactiveCallbacksRegistrar.class)
public class Neo4jReactiveDataAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean
	public ReactiveDatabaseSelectionProvider reactiveDatabaseSelectionProvider(Neo4jDataProperties dataProperties) {
		String database = dataProperties.getDatabase();
		return (database != null) ? ReactiveDatabaseSelectionProvider.createStaticDatabaseSelectionProvider(database)
				: ReactiveDatabaseSelectionProvider.getDefaultSelectionProvider();
	}

	@Bean(ReactiveNeo4jRepositoryConfigurationExtension.DEFAULT_NEO4J_CLIENT_BEAN_NAME)
	@ConditionalOnMissingBean
	public ReactiveNeo4jClient reactiveNeo4jClient(Driver driver) {
		return ReactiveNeo4jClient.create(driver);
	}

	@Bean(ReactiveNeo4jRepositoryConfigurationExtension.DEFAULT_NEO4J_TEMPLATE_BEAN_NAME)
	@ConditionalOnMissingBean(ReactiveNeo4jOperations.class)
	public ReactiveNeo4jTemplate reactiveNeo4jTemplate(ReactiveNeo4jClient neo4jClient,
			Neo4jMappingContext neo4jMappingContext, ReactiveDatabaseSelectionProvider databaseNameProvider) {
		return new ReactiveNeo4jTemplate(neo4jClient, neo4jMappingContext, databaseNameProvider);
	}

}
