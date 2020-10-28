package org.springframework.boot.docs.jdbc;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.commons.dbcp2.BasicDataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * Example configuration for configuring a configurable secondary {@link DataSource} while
 * keeping the auto-configuration defaults for the primary one.
 *

 */
public class SimpleTwoDataSourcesExample {

	/**
	 * A simple configuration that exposes two data sources.
	 */
	@Configuration
	public static class SimpleDataSourcesConfiguration {

		// tag::configuration[]
		@Bean
		@Primary
		@ConfigurationProperties("app.datasource.first")
		public DataSourceProperties firstDataSourceProperties() {
			return new DataSourceProperties();
		}

		@Bean
		@Primary
		@ConfigurationProperties("app.datasource.first.configuration")
		public HikariDataSource firstDataSource() {
			return firstDataSourceProperties().initializeDataSourceBuilder().type(HikariDataSource.class).build();
		}

		@Bean
		@ConfigurationProperties("app.datasource.second")
		public BasicDataSource secondDataSource() {
			return DataSourceBuilder.create().type(BasicDataSource.class).build();
		}
		// end::configuration[]

	}

}
