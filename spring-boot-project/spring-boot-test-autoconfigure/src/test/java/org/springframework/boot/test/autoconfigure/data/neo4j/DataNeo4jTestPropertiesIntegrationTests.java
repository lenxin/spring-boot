package org.springframework.boot.test.autoconfigure.data.neo4j;

import java.time.Duration;

import org.junit.jupiter.api.Test;
import org.testcontainers.containers.Neo4jContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for the {@link DataNeo4jTest#properties properties} attribute of
 * {@link DataNeo4jTest @DataNeo4jTest}.
 *

 */
@Testcontainers(disabledWithoutDocker = true)
@DataNeo4jTest(properties = "spring.profiles.active=test")
class DataNeo4jTestPropertiesIntegrationTests {

	@Container
	static final Neo4jContainer<?> neo4j = new Neo4jContainer<>().withoutAuthentication()
			.withStartupTimeout(Duration.ofMinutes(10));

	@DynamicPropertySource
	static void neo4jProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.neo4j.uri", neo4j::getBoltUrl);
	}

	@Autowired
	private Environment environment;

	@Test
	void environmentWithNewProfile() {
		assertThat(this.environment.getActiveProfiles()).containsExactly("test");
	}

}
