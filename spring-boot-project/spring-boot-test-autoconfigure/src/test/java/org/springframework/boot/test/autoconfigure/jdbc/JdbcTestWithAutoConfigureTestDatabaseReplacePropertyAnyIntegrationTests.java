package org.springframework.boot.test.autoconfigure.jdbc;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for {@link JdbcTest @JdbcTest}.
 *


 */
@JdbcTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.HSQLDB)
@TestPropertySource(properties = "spring.test.database.replace=ANY")
class JdbcTestWithAutoConfigureTestDatabaseReplacePropertyAnyIntegrationTests {

	@Autowired
	private DataSource dataSource;

	@Test
	void replacesDefinedDataSourceWithExplicit() throws Exception {
		// H2 is explicitly defined but HSQL is the override.
		String product = this.dataSource.getConnection().getMetaData().getDatabaseProductName();
		assertThat(product).startsWith("HSQL");
	}

	@Configuration(proxyBeanMethods = false)
	@EnableAutoConfiguration
	static class Config {

		@Bean
		DataSource dataSource() {
			return new EmbeddedDatabaseBuilder().generateUniqueName(true).setType(EmbeddedDatabaseType.H2).build();
		}

	}

}
