package org.springframework.boot.autoconfigure.orm.jpa;

import java.sql.Connection;
import java.sql.DatabaseMetaData;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;

import org.springframework.orm.jpa.vendor.Database;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

/**
 * Tests for {@link DatabaseLookup}.
 *


 */
class DatabaseLookupTests {

	@Test
	void getDatabaseWhenDataSourceIsNullShouldReturnDefault() {
		assertThat(DatabaseLookup.getDatabase(null)).isEqualTo(Database.DEFAULT);
	}

	@Test
	void getDatabaseWhenDataSourceIsUnknownShouldReturnDefault() throws Exception {
		testGetDatabase("jdbc:idontexist:", Database.DEFAULT);
	}

	@Test
	void getDatabaseWhenDerbyShouldReturnDerby() throws Exception {
		testGetDatabase("jdbc:derby:", Database.DERBY);
	}

	@Test
	void getDatabaseWhenH2ShouldReturnH2() throws Exception {
		testGetDatabase("jdbc:h2:", Database.H2);
	}

	@Test
	void getDatabaseWhenHsqldbShouldReturnHsqldb() throws Exception {
		testGetDatabase("jdbc:hsqldb:", Database.HSQL);
	}

	@Test
	void getDatabaseWhenMysqlShouldReturnMysql() throws Exception {
		testGetDatabase("jdbc:mysql:", Database.MYSQL);
	}

	@Test
	void getDatabaseWhenOracleShouldReturnOracle() throws Exception {
		testGetDatabase("jdbc:oracle:", Database.ORACLE);
	}

	@Test
	void getDatabaseWhenPostgresShouldReturnPostgres() throws Exception {
		testGetDatabase("jdbc:postgresql:", Database.POSTGRESQL);
	}

	@Test
	void getDatabaseWhenSqlserverShouldReturnSqlserver() throws Exception {
		testGetDatabase("jdbc:sqlserver:", Database.SQL_SERVER);
	}

	@Test
	void getDatabaseWhenDb2ShouldReturnDb2() throws Exception {
		testGetDatabase("jdbc:db2:", Database.DB2);
	}

	@Test
	void getDatabaseWhenInformixShouldReturnInformix() throws Exception {
		testGetDatabase("jdbc:informix-sqli:", Database.INFORMIX);
	}

	@Test
	void getDatabaseWhenSapShouldReturnHana() throws Exception {
		testGetDatabase("jdbc:sap:", Database.HANA);
	}

	private void testGetDatabase(String url, Database expected) throws Exception {
		DataSource dataSource = mock(DataSource.class);
		Connection connection = mock(Connection.class);
		DatabaseMetaData metaData = mock(DatabaseMetaData.class);
		given(dataSource.getConnection()).willReturn(connection);
		given(connection.getMetaData()).willReturn(metaData);
		given(metaData.getURL()).willReturn(url);
		Database database = DatabaseLookup.getDatabase(dataSource);
		assertThat(database).isEqualTo(expected);
	}

}
