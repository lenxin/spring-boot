package org.springframework.boot.test.autoconfigure.data.neo4j;

import java.time.Duration;

import org.junit.jupiter.api.Test;
import org.testcontainers.containers.Neo4jContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.stereotype.Service;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration test with custom include filter for {@link DataNeo4jTest @DataNeo4jTest}.
 *


 */
@Testcontainers(disabledWithoutDocker = true)
@DataNeo4jTest(includeFilters = @Filter(Service.class))
class DataNeo4jTestWithIncludeFilterIntegrationTests {

	@Container
	static final Neo4jContainer<?> neo4j = new Neo4jContainer<>().withoutAuthentication()
			.withStartupTimeout(Duration.ofMinutes(10));

	@DynamicPropertySource
	static void neo4jProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.neo4j.uri", neo4j::getBoltUrl);
	}

	@Autowired
	private ExampleService service;

	@Test
	void testService() {
		assertThat(this.service.hasNode(ExampleGraph.class)).isFalse();
	}

}
