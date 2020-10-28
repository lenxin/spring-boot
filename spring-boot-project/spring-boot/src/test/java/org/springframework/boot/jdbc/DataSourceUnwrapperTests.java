package org.springframework.boot.jdbc;

import java.sql.SQLException;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.tomcat.jdbc.pool.DataSourceProxy;
import org.junit.jupiter.api.Test;

import org.springframework.aop.framework.ProxyFactory;
import org.springframework.jdbc.datasource.DelegatingDataSource;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

/**
 * Tests for {@link DataSourceUnwrapper}.
 *

 */
class DataSourceUnwrapperTests {

	@Test
	void unwrapWithTarget() {
		DataSource dataSource = new HikariDataSource();
		assertThat(DataSourceUnwrapper.unwrap(dataSource, HikariDataSource.class)).isSameAs(dataSource);
	}

	@Test
	void unwrapWithWrongTarget() {
		DataSource dataSource = new HikariDataSource();
		assertThat(DataSourceUnwrapper.unwrap(dataSource, SingleConnectionDataSource.class)).isNull();
	}

	@Test
	void unwrapWithDelegate() {
		DataSource dataSource = new HikariDataSource();
		DataSource actual = wrapInDelegate(wrapInDelegate(dataSource));
		assertThat(DataSourceUnwrapper.unwrap(actual, HikariDataSource.class)).isSameAs(dataSource);
	}

	@Test
	void unwrapWithProxy() {
		DataSource dataSource = new HikariDataSource();
		DataSource actual = wrapInProxy(wrapInProxy(dataSource));
		assertThat(DataSourceUnwrapper.unwrap(actual, HikariDataSource.class)).isSameAs(dataSource);
	}

	@Test
	void unwrapWithProxyAndDelegate() {
		DataSource dataSource = new HikariDataSource();
		DataSource actual = wrapInProxy(wrapInDelegate(dataSource));
		assertThat(DataSourceUnwrapper.unwrap(actual, HikariDataSource.class)).isSameAs(dataSource);
	}

	@Test
	void unwrapWithSeveralLevelOfWrapping() {
		DataSource dataSource = new HikariDataSource();
		DataSource actual = wrapInProxy(wrapInDelegate(wrapInDelegate(wrapInProxy(wrapInDelegate(dataSource)))));
		assertThat(DataSourceUnwrapper.unwrap(actual, HikariDataSource.class)).isSameAs(dataSource);
	}

	@Test
	void unwrapDataSourceProxy() {
		org.apache.tomcat.jdbc.pool.DataSource dataSource = new org.apache.tomcat.jdbc.pool.DataSource();
		DataSource actual = wrapInDelegate(wrapInProxy(dataSource));
		assertThat(DataSourceUnwrapper.unwrap(actual, DataSourceProxy.class)).isSameAs(dataSource);
	}

	@Test
	void unwrappingIsNotAttemptedWhenDataSourceIsNotWrapperForTarget() throws SQLException {
		DataSource dataSource = mock(DataSource.class);
		DataSource actual = DataSourceUnwrapper.unwrap(dataSource, HikariDataSource.class);
		assertThat(actual).isNull();
		verify(dataSource).isWrapperFor(HikariDataSource.class);
		verifyNoMoreInteractions(dataSource);
	}

	private DataSource wrapInProxy(DataSource dataSource) {
		return (DataSource) new ProxyFactory(dataSource).getProxy();
	}

	private DataSource wrapInDelegate(DataSource dataSource) {
		return new DelegatingDataSource(dataSource);
	}

}
