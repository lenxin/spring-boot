package org.springframework.boot.autoconfigure.jooq;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.jooq.SQLDialect;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

/**
 * Tests for {@link JooqProperties}.
 *

 */
class JooqPropertiesTests {

	private AnnotationConfigApplicationContext context;

	@AfterEach
	void close() {
		if (this.context != null) {
			this.context.close();
		}
	}

	@Test
	void determineSqlDialectNoCheckIfDialectIsSet() throws SQLException {
		JooqProperties properties = load("spring.jooq.sql-dialect=postgres");
		DataSource dataSource = mockStandaloneDataSource();
		SQLDialect sqlDialect = properties.determineSqlDialect(dataSource);
		assertThat(sqlDialect).isEqualTo(SQLDialect.POSTGRES);
		verify(dataSource, never()).getConnection();
	}

	@Test
	void determineSqlDialectWithKnownUrl() {
		JooqProperties properties = load();
		SQLDialect sqlDialect = properties.determineSqlDialect(mockDataSource("jdbc:h2:mem:testdb"));
		assertThat(sqlDialect).isEqualTo(SQLDialect.H2);
	}

	@Test
	void determineSqlDialectWithKnownUrlAndUserConfig() {
		JooqProperties properties = load("spring.jooq.sql-dialect=mysql");
		SQLDialect sqlDialect = properties.determineSqlDialect(mockDataSource("jdbc:h2:mem:testdb"));
		assertThat(sqlDialect).isEqualTo(SQLDialect.MYSQL);
	}

	@Test
	void determineSqlDialectWithUnknownUrl() {
		JooqProperties properties = load();
		SQLDialect sqlDialect = properties.determineSqlDialect(mockDataSource("jdbc:unknown://localhost"));
		assertThat(sqlDialect).isEqualTo(SQLDialect.DEFAULT);
	}

	private DataSource mockStandaloneDataSource() throws SQLException {
		DataSource ds = mock(DataSource.class);
		given(ds.getConnection()).willThrow(SQLException.class);
		return ds;
	}

	private DataSource mockDataSource(String jdbcUrl) {
		DataSource ds = mock(DataSource.class);
		try {
			DatabaseMetaData metadata = mock(DatabaseMetaData.class);
			given(metadata.getURL()).willReturn(jdbcUrl);
			Connection connection = mock(Connection.class);
			given(connection.getMetaData()).willReturn(metadata);
			given(ds.getConnection()).willReturn(connection);
		}
		catch (SQLException ex) {
			// Do nothing
		}
		return ds;
	}

	private JooqProperties load(String... environment) {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		TestPropertyValues.of(environment).applyTo(ctx);
		ctx.register(TestConfiguration.class);
		ctx.refresh();
		this.context = ctx;
		return this.context.getBean(JooqProperties.class);
	}

	@Configuration(proxyBeanMethods = false)
	@EnableConfigurationProperties(JooqProperties.class)
	static class TestConfiguration {

	}

}
