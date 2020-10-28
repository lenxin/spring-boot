package org.springframework.boot.docs.jdbc;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariDataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * Example configuration for configuring a configurable custom {@link DataSource}.
 *

 */
public class ConfigurableDataSourceExample {

	/**
	 * A configuration that defines dedicated settings and reuses
	 * {@link DataSourceProperties}.
	 */
	@Configuration(proxyBeanMethods = false)
	public static class ConfigurableDataSourceConfiguration {

		// tag::configuration[]
		@Bean
		@Primary
		@ConfigurationProperties("app.datasource")
		public DataSourceProperties dataSourceProperties() {
			return new DataSourceProperties();
		}

		@Bean
		@ConfigurationProperties("app.datasource.configuration")
		public HikariDataSource dataSource(DataSourceProperties properties) {
			return properties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
		}
		// end::configuration[]

	}

}
