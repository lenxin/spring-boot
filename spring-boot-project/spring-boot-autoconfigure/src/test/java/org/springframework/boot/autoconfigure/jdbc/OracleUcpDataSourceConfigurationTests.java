package org.springframework.boot.autoconfigure.jdbc;

import java.sql.Connection;

import javax.sql.DataSource;

import oracle.ucp.jdbc.PoolDataSource;
import oracle.ucp.jdbc.PoolDataSourceImpl;
import org.junit.jupiter.api.Test;

import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link DataSourceAutoConfiguration} with Oracle UCP.
 *


 */
class OracleUcpDataSourceConfigurationTests {

	private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
			.withConfiguration(AutoConfigurations.of(DataSourceAutoConfiguration.class))
			.withPropertyValues("spring.datasource.initialization-mode=never",
					"spring.datasource.type=" + PoolDataSource.class.getName());

	@Test
	void testDataSourceExists() {
		this.contextRunner.run((context) -> {
			assertThat(context.getBeansOfType(DataSource.class)).hasSize(1);
			assertThat(context.getBeansOfType(PoolDataSourceImpl.class)).hasSize(1);
			try (Connection connection = context.getBean(DataSource.class).getConnection()) {
				assertThat(connection.isValid(1000)).isTrue();
			}
		});
	}

	@Test
	void testDataSourcePropertiesOverridden() {
		this.contextRunner.withPropertyValues("spring.datasource.oracleucp.url=jdbc:foo//bar/spam",
				"spring.datasource.oracleucp.max-idle-time=1234").run((context) -> {
					PoolDataSourceImpl ds = context.getBean(PoolDataSourceImpl.class);
					assertThat(ds.getURL()).isEqualTo("jdbc:foo//bar/spam");
					assertThat(ds.getMaxIdleTime()).isEqualTo(1234);
				});
	}

	@Test
	void testDataSourceConnectionPropertiesOverridden() {
		this.contextRunner.withPropertyValues("spring.datasource.oracleucp.connection-properties.autoCommit=false")
				.run((context) -> {
					PoolDataSourceImpl ds = context.getBean(PoolDataSourceImpl.class);
					assertThat(ds.getConnectionProperty("autoCommit")).isEqualTo("false");
				});
	}

	@Test
	void testDataSourceDefaultsPreserved() {
		this.contextRunner.run((context) -> {
			PoolDataSourceImpl ds = context.getBean(PoolDataSourceImpl.class);
			assertThat(ds.getInitialPoolSize()).isEqualTo(0);
			assertThat(ds.getMinPoolSize()).isEqualTo(0);
			assertThat(ds.getMaxPoolSize()).isEqualTo(Integer.MAX_VALUE);
			assertThat(ds.getInactiveConnectionTimeout()).isEqualTo(0);
			assertThat(ds.getConnectionWaitTimeout()).isEqualTo(3);
			assertThat(ds.getTimeToLiveConnectionTimeout()).isEqualTo(0);
			assertThat(ds.getAbandonedConnectionTimeout()).isEqualTo(0);
			assertThat(ds.getTimeoutCheckInterval()).isEqualTo(30);
			assertThat(ds.getFastConnectionFailoverEnabled()).isFalse();
		});
	}

	@Test
	void nameIsAliasedToPoolName() {
		this.contextRunner.withPropertyValues("spring.datasource.name=myDS").run((context) -> {
			PoolDataSourceImpl ds = context.getBean(PoolDataSourceImpl.class);
			assertThat(ds.getConnectionPoolName()).isEqualTo("myDS");
		});
	}

	@Test
	void poolNameTakesPrecedenceOverName() {
		this.contextRunner.withPropertyValues("spring.datasource.name=myDS",
				"spring.datasource.oracleucp.connection-pool-name=myOracleUcpDS").run((context) -> {
					PoolDataSourceImpl ds = context.getBean(PoolDataSourceImpl.class);
					assertThat(ds.getConnectionPoolName()).isEqualTo("myOracleUcpDS");
				});
	}

}
