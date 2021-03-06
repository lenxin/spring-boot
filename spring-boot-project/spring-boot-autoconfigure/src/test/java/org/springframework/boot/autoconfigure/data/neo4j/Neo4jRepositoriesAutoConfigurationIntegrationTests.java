package org.springframework.boot.autoconfigure.data.neo4j;

import org.junit.jupiter.api.Test;
import org.testcontainers.containers.Neo4jContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.data.neo4j.country.CountryRepository;
import org.springframework.boot.autoconfigure.neo4j.Neo4jAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test to ensure that the properties get read and applied during the auto-configuration.
 *

 */
@SpringBootTest
@Testcontainers(disabledWithoutDocker = true)
public class Neo4jRepositoriesAutoConfigurationIntegrationTests {

	@Container
	private static final Neo4jContainer<?> neo4jServer = new Neo4jContainer<>("neo4j:4.0");

	@DynamicPropertySource
	static void neo4jProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.neo4j.uri", neo4jServer::getBoltUrl);
		registry.add("spring.neo4j.authentication.username", () -> "neo4j");
		registry.add("spring.neo4j.authentication.password", neo4jServer::getAdminPassword);
	}

	@Autowired
	private CountryRepository countryRepository;

	@Test
	void ensureRepositoryIsReady() {
		assertThat(this.countryRepository.count()).isEqualTo(0);
	}

	@Configuration
	@EnableNeo4jRepositories(basePackageClasses = CountryRepository.class)
	@ImportAutoConfiguration({ Neo4jAutoConfiguration.class, Neo4jDataAutoConfiguration.class,
			Neo4jRepositoriesAutoConfiguration.class })
	static class TestConfiguration {

	}

}
