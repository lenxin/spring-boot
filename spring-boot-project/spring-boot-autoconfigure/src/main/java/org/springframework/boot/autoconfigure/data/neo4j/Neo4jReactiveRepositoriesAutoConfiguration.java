package org.springframework.boot.autoconfigure.data.neo4j;

import org.neo4j.driver.Driver;
import reactor.core.publisher.Flux;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.ConditionalOnRepositoryType;
import org.springframework.boot.autoconfigure.data.RepositoryType;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.neo4j.repository.ReactiveNeo4jRepository;
import org.springframework.data.neo4j.repository.config.ReactiveNeo4jRepositoryConfigurationExtension;
import org.springframework.data.neo4j.repository.support.ReactiveNeo4jRepositoryFactoryBean;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for Spring Data's Neo4j Reactive
 * Repositories.
 *


 * @since 2.4.0
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass({ Driver.class, ReactiveNeo4jRepository.class, Flux.class })
@ConditionalOnMissingBean({ ReactiveNeo4jRepositoryFactoryBean.class,
		ReactiveNeo4jRepositoryConfigurationExtension.class })
@ConditionalOnRepositoryType(store = "neo4j", type = RepositoryType.REACTIVE)
@Import(Neo4jReactiveRepositoriesRegistrar.class)
@AutoConfigureAfter(Neo4jReactiveDataAutoConfiguration.class)
public class Neo4jReactiveRepositoriesAutoConfiguration {

}
