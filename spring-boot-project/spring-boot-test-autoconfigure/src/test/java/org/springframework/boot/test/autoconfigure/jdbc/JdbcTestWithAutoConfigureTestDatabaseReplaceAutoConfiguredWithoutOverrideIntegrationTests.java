package org.springframework.boot.test.autoconfigure.jdbc;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for {@link JdbcTest @JdbcTest}.
 *


 */
@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.AUTO_CONFIGURED)
class JdbcTestWithAutoConfigureTestDatabaseReplaceAutoConfiguredWithoutOverrideIntegrationTests {

	@Autowired
	private DataSource dataSource;

	@Test
	void usesDefaultEmbeddedDatabase() throws Exception {
		String product = this.dataSource.getConnection().getMetaData().getDatabaseProductName();
		// @AutoConfigureTestDatabase would use H2 but HSQL is manually defined
		assertThat(product).startsWith("HSQL");
	}

}
