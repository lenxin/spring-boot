package org.springframework.boot.test.autoconfigure.data.jdbc;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.springframework.boot.test.autoconfigure.AutoConfigurationImportedCondition.importedAutoConfiguration;

/**
 * Integration tests for {@link DataJdbcTest @DataJdbcTest}.
 *

 */
@DataJdbcTest
@TestPropertySource(
		properties = "spring.datasource.schema=classpath:org/springframework/boot/test/autoconfigure/data/jdbc/schema.sql")
class DataJdbcTestIntegrationTests {

	@Autowired
	private ExampleRepository repository;

	@Autowired
	private DataSource dataSource;

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Test
	void testRepository() {
		this.jdbcTemplate.update("INSERT INTO EXAMPLE_ENTITY (id, name, reference) VALUES (1, 'a', 'alpha')");
		this.jdbcTemplate.update("INSERT INTO EXAMPLE_ENTITY (id, name, reference) VALUES (2, 'b', 'bravo')");
		assertThat(this.repository.findAll()).hasSize(2);
	}

	@Test
	void replacesDefinedDataSourceWithEmbeddedDefault() throws Exception {
		String product = this.dataSource.getConnection().getMetaData().getDatabaseProductName();
		assertThat(product).isEqualTo("H2");
	}

	@Test
	void didNotInjectExampleComponent() {
		assertThatExceptionOfType(NoSuchBeanDefinitionException.class)
				.isThrownBy(() -> this.applicationContext.getBean(ExampleComponent.class));
	}

	@Test
	void flywayAutoConfigurationWasImported() {
		assertThat(this.applicationContext).has(importedAutoConfiguration(FlywayAutoConfiguration.class));
	}

	@Test
	void liquibaseAutoConfigurationWasImported() {
		assertThat(this.applicationContext).has(importedAutoConfiguration(LiquibaseAutoConfiguration.class));
	}

}
