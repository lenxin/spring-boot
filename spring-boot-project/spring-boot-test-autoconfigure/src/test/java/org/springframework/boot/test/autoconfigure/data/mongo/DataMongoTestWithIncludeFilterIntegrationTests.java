package org.springframework.boot.test.autoconfigure.data.mongo;

import java.time.Duration;

import org.junit.jupiter.api.Test;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.stereotype.Service;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration test with custom include filter for {@link DataMongoTest @DataMongoTest}.
 *

 */
@DataMongoTest(includeFilters = @Filter(Service.class))
@Testcontainers(disabledWithoutDocker = true)
class DataMongoTestWithIncludeFilterIntegrationTests {

	@Container
	static final MongoDBContainer mongoDB = new MongoDBContainer().withStartupAttempts(5)
			.withStartupTimeout(Duration.ofMinutes(5));

	@Autowired
	private ExampleService service;

	@DynamicPropertySource
	static void mongoProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.data.mongodb.uri", mongoDB::getReplicaSetUrl);
	}

	@Test
	void testService() {
		assertThat(this.service.hasCollection("foobar")).isFalse();
	}

}
