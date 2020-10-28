package org.springframework.boot.test.autoconfigure.data.mongo;

import java.time.Duration;

import org.junit.jupiter.api.Test;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Sample tests for {@link DataMongoTest @DataMongoTest} using reactive repositories.
 *

 */
@DataMongoTest
@Testcontainers(disabledWithoutDocker = true)
class DataMongoTestReactiveIntegrationTests {

	@Container
	static final MongoDBContainer mongoDB = new MongoDBContainer().withStartupAttempts(5)
			.withStartupTimeout(Duration.ofMinutes(5));

	@Autowired
	private ReactiveMongoTemplate mongoTemplate;

	@Autowired
	private ExampleReactiveRepository exampleRepository;

	@DynamicPropertySource
	static void mongoProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.data.mongodb.uri", mongoDB::getReplicaSetUrl);
	}

	@Test
	void testRepository() {
		ExampleDocument exampleDocument = new ExampleDocument();
		exampleDocument.setText("Look, new @DataMongoTest!");
		exampleDocument = this.exampleRepository.save(exampleDocument).block(Duration.ofSeconds(30));
		assertThat(exampleDocument.getId()).isNotNull();
		assertThat(this.mongoTemplate.collectionExists("exampleDocuments").block(Duration.ofSeconds(30))).isTrue();
	}

}
