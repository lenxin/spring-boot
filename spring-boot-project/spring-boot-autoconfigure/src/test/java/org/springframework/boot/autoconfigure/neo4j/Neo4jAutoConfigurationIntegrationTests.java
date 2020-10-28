package org.springframework.boot.autoconfigure.neo4j;

import org.junit.jupiter.api.Test;
import org.neo4j.driver.Driver;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;
import org.neo4j.driver.Transaction;
import org.testcontainers.containers.Neo4jContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for {@link Neo4jAutoConfiguration}.
 *


 */
@SpringBootTest
@Testcontainers(disabledWithoutDocker = true)
class Neo4jAutoConfigurationIntegrationTests {

	@Container
	private static final Neo4jContainer<?> neo4jServer = new Neo4jContainer<>("neo4j:4.0");

	@DynamicPropertySource
	static void neo4jProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.neo4j.uri", neo4jServer::getBoltUrl);
		registry.add("spring.neo4j.authentication.username", () -> "neo4j");
		registry.add("spring.neo4j.authentication.password", neo4jServer::getAdminPassword);
	}

	@Autowired
	private Driver driver;

	@Test
	void driverCanHandleRequest() {
		try (Session session = this.driver.session(); Transaction tx = session.beginTransaction()) {
			Result statementResult = tx.run("MATCH (n:Thing) RETURN n LIMIT 1");
			assertThat(statementResult.hasNext()).isFalse();
			tx.commit();
		}
	}

	@Configuration(proxyBeanMethods = false)
	@ImportAutoConfiguration(Neo4jAutoConfiguration.class)
	static class TestConfiguration {

	}

}
