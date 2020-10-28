package org.springframework.boot.docs.jdbc;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.commons.dbcp2.BasicDataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * Example configuration for configuring two data sources with what Spring Boot does in
 * auto-configuration.
 *

 */
public class CompleteTwoDataSourcesExample {

	/**
	 * A complete configuration that exposes two data sources.
	 */
	@Configuration
	public static class CompleteDataSourcesConfiguration {

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
		public DataSourceProperties secondDataSourceProperties() {
			return new DataSourceProperties();
		}

		@Bean
		@ConfigurationProperties("app.datasource.second.configuration")
		public BasicDataSource secondDataSource() {
			return secondDataSourceProperties().initializeDataSourceBuilder().type(BasicDataSource.class).build();
		}
		// end::configuration[]

	}

}
