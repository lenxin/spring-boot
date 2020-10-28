package org.springframework.boot.docs.jdbc;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariDataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Example configuration for configuring a simple {@link DataSource}.
 *

 */
public class SimpleDataSourceExample {

	/**
	 * A simple configuration that exposes dedicated settings.
	 */
	@Configuration(proxyBeanMethods = false)
	public static class SimpleDataSourceConfiguration {

		// tag::configuration[]
		@Bean
		@ConfigurationProperties("app.datasource")
		public HikariDataSource dataSource() {
			return DataSourceBuilder.create().type(HikariDataSource.class).build();
		}
		// end::configuration[]

	}

}
