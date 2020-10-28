package org.springframework.boot.test.autoconfigure.data.neo4j;

import java.time.Duration;

import org.junit.jupiter.api.Test;
import org.neo4j.driver.Driver;
import org.testcontainers.containers.Neo4jContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.neo4j.core.ReactiveDatabaseSelectionProvider;
import org.springframework.data.neo4j.core.ReactiveNeo4jTemplate;
import org.springframework.data.neo4j.core.transaction.ReactiveNeo4jTransactionManager;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

/**
 * Integration tests for {@link DataNeo4jTest @DataNeo4jTest} with reactive style.
 *


 * @since 2.4.0
 */
@DataNeo4jTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
@Testcontainers(disabledWithoutDocker = true)
class DataNeo4jTestReactiveIntegrationTests {

	@Container
	static final Neo4jContainer<?> neo4j = new Neo4jContainer<>("neo4j:4.0").withoutAuthentication()
			.withStartupTimeout(Duration.ofMinutes(10));

	@DynamicPropertySource
	static void neo4jProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.neo4j.uri", neo4j::getBoltUrl);
	}

	@Autowired
	private ReactiveNeo4jTemplate neo4jTemplate;

	@Autowired
	private ExampleReactiveRepository exampleRepository;

	@Autowired
	private ApplicationContext applicationContext;

	@Test
	void testRepository() {
		Mono.just(new ExampleGraph("Look, new @DataNeo4jTest with reactive!")).flatMap(this.exampleRepository::save)
				.as(StepVerifier::create).expectNextCount(1).verifyComplete();
		StepVerifier.create(this.neo4jTemplate.count(ExampleGraph.class)).expectNext(1L).verifyComplete();
	}

	@Test
	void didNotInjectExampleService() {
		assertThatExceptionOfType(NoSuchBeanDefinitionException.class)
				.isThrownBy(() -> this.applicationContext.getBean(ExampleService.class));
	}

	@TestConfiguration(proxyBeanMethods = false)
	static class ReactiveTransactionManagerConfiguration {

		@Bean
		ReactiveNeo4jTransactionManager reactiveTransactionManager(Driver driver,
				ReactiveDatabaseSelectionProvider databaseNameProvider) {
			return new ReactiveNeo4jTransactionManager(driver, databaseNameProvider);
		}

	}

}
