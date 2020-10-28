package org.springframework.boot.test.autoconfigure.data.r2dbc;

import io.r2dbc.spi.ConnectionFactory;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;
import org.springframework.r2dbc.core.DatabaseClient;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for {@link DataR2dbcTest}.
 *

 */
@DataR2dbcTest
class DataR2dbcTestIntegrationTests {

	@Autowired
	private DatabaseClient databaseClient;

	@Autowired
	private ConnectionFactory connectionFactory;

	@Autowired
	private ApplicationContext applicationContext;

	@Test
	void testDatabaseClient() {
		this.databaseClient.sql("SELECT * FROM example").fetch().all().as(StepVerifier::create).verifyComplete();
	}

	@Test
	void replacesDefinedConnectionFactoryWithEmbeddedDefault() {
		String product = this.connectionFactory.getMetadata().getName();
		assertThat(product).isEqualTo("H2");
	}

	@Test
	void registersExampleRepository() {
		assertThat(this.applicationContext.getBeanNamesForType(ExampleRepository.class)).isNotEmpty();
	}

	@TestConfiguration
	static class DatabaseInitializationConfiguration {

		@Autowired
		void initializeDatabase(ConnectionFactory connectionFactory) {
			ResourceLoader resourceLoader = new DefaultResourceLoader();
			Resource[] scripts = new Resource[] { resourceLoader
					.getResource("classpath:org/springframework/boot/test/autoconfigure/data/r2dbc/schema.sql") };
			new ResourceDatabasePopulator(scripts).populate(connectionFactory).block();
		}

	}

}
