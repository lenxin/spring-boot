package org.springframework.boot.test.autoconfigure.jdbc;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for {@link JdbcTest @JdbcTest}.
 *


 */
@JdbcTest
@TestPropertySource(properties = "spring.test.database.replace=NONE")
class JdbcTestWithAutoConfigureTestDatabaseReplacePropertyNoneIntegrationTests {

	@Autowired
	private DataSource dataSource;

	@Test
	void usesDefaultEmbeddedDatabase() throws Exception {
		// HSQL is explicitly defined and should not be replaced
		String product = this.dataSource.getConnection().getMetaData().getDatabaseProductName();
		assertThat(product).startsWith("HSQL");
	}

}
