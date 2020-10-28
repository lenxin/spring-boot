package org.springframework.boot.test.autoconfigure.data.mongo;

import java.time.Duration;

import org.junit.jupiter.api.Test;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for using {@link DataMongoTest @DataMongoTest} with transactions.
 *

 */
@DataMongoTest
@Transactional
@Testcontainers(disabledWithoutDocker = true)
class TransactionalDataMongoTestIntegrationTests {

	@Container
	static final MongoDBContainer mongoDB = new MongoDBContainer().withStartupAttempts(5)
			.withStartupTimeout(Duration.ofMinutes(5));

	@Autowired
	private ExampleRepository exampleRepository;

	@Test
	void testRepository() {
		ExampleDocument exampleDocument = new ExampleDocument();
		exampleDocument.setText("Look, new @DataMongoTest!");
		exampleDocument = this.exampleRepository.save(exampleDocument);
		assertThat(exampleDocument.getId()).isNotNull();
	}

	@DynamicPropertySource
	static void mongoProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.data.mongodb.uri", mongoDB::getReplicaSetUrl);
	}

	@TestConfiguration(proxyBeanMethods = false)
	static class TransactionManagerConfiguration {

		@Bean
		MongoTransactionManager mongoTransactionManager(MongoDatabaseFactory dbFactory) {
			return new MongoTransactionManager(dbFactory);
		}

	}

	@TestConfiguration(proxyBeanMethods = false)
	static class MongoInitializationConfiguration {

		@Bean
		MongoInitializer mongoInitializer(MongoTemplate template) {
			return new MongoInitializer(template);
		}

		static class MongoInitializer implements InitializingBean {

			private final MongoTemplate template;

			MongoInitializer(MongoTemplate template) {
				this.template = template;
			}

			@Override
			public void afterPropertiesSet() throws Exception {
				this.template.createCollection("exampleDocuments");
			}

		}

	}

}
