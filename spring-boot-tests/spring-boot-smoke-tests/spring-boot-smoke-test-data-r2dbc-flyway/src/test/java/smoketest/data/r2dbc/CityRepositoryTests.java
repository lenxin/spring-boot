package smoketest.data.r2dbc;

import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import reactor.test.StepVerifier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link CityRepository}.
 */
@Testcontainers(disabledWithoutDocker = true)
@DataR2dbcTest
class CityRepositoryTests {

	@Container
	static PostgreSQLContainer<?> postgresql = new PostgreSQLContainer<>().withDatabaseName("test_flyway");

	@DynamicPropertySource
	static void postgresqlProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.r2dbc.url", CityRepositoryTests::r2dbcUrl);
		registry.add("spring.r2dbc.username", postgresql::getUsername);
		registry.add("spring.r2dbc.password", postgresql::getPassword);

		// configure flyway to use the same database
		registry.add("spring.flyway.url", postgresql::getJdbcUrl);
		registry.add("spring.flyway.user", postgresql::getUsername);
		registry.add("spring.flyway.password", postgresql::getPassword);
	}

	@Autowired
	private CityRepository repository;

	@Test
	void databaseHasBeenInitialized() {
		StepVerifier.create(this.repository.findByState("DC").filter((city) -> city.getName().equals("Washington")))
				.consumeNextWith((city) -> assertThat(city.getId()).isNotNull()).verifyComplete();
	}

	private static String r2dbcUrl() {
		return String.format("r2dbc:postgresql://%s:%s/%s", postgresql.getHost(),
				postgresql.getMappedPort(PostgreSQLContainer.POSTGRESQL_PORT), postgresql.getDatabaseName());
	}

}
