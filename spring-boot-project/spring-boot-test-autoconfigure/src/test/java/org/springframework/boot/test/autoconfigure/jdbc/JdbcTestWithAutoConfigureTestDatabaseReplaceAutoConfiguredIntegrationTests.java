package org.springframework.boot.test.autoconfigure.jdbc;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.context.annotation.Configuration;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for {@link JdbcTest @JdbcTest}.
 *


 */
@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.AUTO_CONFIGURED,
		connection = EmbeddedDatabaseConnection.HSQLDB)
class JdbcTestWithAutoConfigureTestDatabaseReplaceAutoConfiguredIntegrationTests {

	@Autowired
	private DataSource dataSource;

	@Test
	void replacesAutoConfiguredDataSource() throws Exception {
		String product = this.dataSource.getConnection().getMetaData().getDatabaseProductName();
		assertThat(product).startsWith("HSQL");
	}

	@Configuration(proxyBeanMethods = false)
	@EnableAutoConfiguration // Will auto-configure H2
	static class Config {

	}

}
