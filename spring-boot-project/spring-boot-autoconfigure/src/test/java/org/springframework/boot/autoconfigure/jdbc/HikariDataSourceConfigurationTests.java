package org.springframework.boot.autoconfigure.jdbc;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.Test;

import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link DataSourceAutoConfiguration} with Hikari.
 *


 */
class HikariDataSourceConfigurationTests {

	private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
			.withConfiguration(AutoConfigurations.of(DataSourceAutoConfiguration.class))
			.withPropertyValues("spring.datasource.initialization-mode=never",
					"spring.datasource.type=" + HikariDataSource.class.getName());

	@Test
	void testDataSourceExists() {
		this.contextRunner.run((context) -> {
			assertThat(context.getBeansOfType(DataSource.class)).hasSize(1);
			assertThat(context.getBeansOfType(HikariDataSource.class)).hasSize(1);
		});
	}

	@Test
	void testDataSourcePropertiesOverridden() {
		this.contextRunner.withPropertyValues("spring.datasource.hikari.jdbc-url=jdbc:foo//bar/spam",
				"spring.datasource.hikari.max-lifetime=1234").run((context) -> {
					HikariDataSource ds = context.getBean(HikariDataSource.class);
					assertThat(ds.getJdbcUrl()).isEqualTo("jdbc:foo//bar/spam");
					assertThat(ds.getMaxLifetime()).isEqualTo(1234);
				});
	}

	@Test
	void testDataSourceGenericPropertiesOverridden() {
		this.contextRunner
				.withPropertyValues(
						"spring.datasource.hikari.data-source-properties.dataSourceClassName=org.h2.JDBCDataSource")
				.run((context) -> {
					HikariDataSource ds = context.getBean(HikariDataSource.class);
					assertThat(ds.getDataSourceProperties().getProperty("dataSourceClassName"))
							.isEqualTo("org.h2.JDBCDataSource");

				});
	}

	@Test
	void testDataSourceDefaultsPreserved() {
		this.contextRunner.run((context) -> {
			HikariDataSource ds = context.getBean(HikariDataSource.class);
			assertThat(ds.getMaxLifetime()).isEqualTo(1800000);
		});
	}

	@Test
	void nameIsAliasedToPoolName() {
		this.contextRunner.withPropertyValues("spring.datasource.name=myDS").run((context) -> {
			HikariDataSource ds = context.getBean(HikariDataSource.class);
			assertThat(ds.getPoolName()).isEqualTo("myDS");

		});
	}

	@Test
	void poolNameTakesPrecedenceOverName() {
		this.contextRunner
				.withPropertyValues("spring.datasource.name=myDS", "spring.datasource.hikari.pool-name=myHikariDS")
				.run((context) -> {
					HikariDataSource ds = context.getBean(HikariDataSource.class);
					assertThat(ds.getPoolName()).isEqualTo("myHikariDS");
				});
	}

}
