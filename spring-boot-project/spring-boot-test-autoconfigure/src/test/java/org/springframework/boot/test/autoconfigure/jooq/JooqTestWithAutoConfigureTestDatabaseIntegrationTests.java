package org.springframework.boot.test.autoconfigure.jooq;

import javax.sql.DataSource;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for {@link JooqTest @JooqTest}.
 *

 */
@JooqTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class JooqTestWithAutoConfigureTestDatabaseIntegrationTests {

	@Autowired
	private DSLContext dsl;

	@Autowired
	private DataSource dataSource;

	@Test
	void replacesAutoConfiguredDataSource() throws Exception {
		String product = this.dataSource.getConnection().getMetaData().getDatabaseProductName();
		assertThat(product).startsWith("H2");
		assertThat(this.dsl.configuration().dialect()).isEqualTo(SQLDialect.H2);
	}

}
