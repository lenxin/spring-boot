package org.springframework.boot.docs.jdbc;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Example configuration for configuring a very basic custom {@link DataSource}.
 *

 */
public class BasicDataSourceExample {

	/**
	 * A configuration that exposes an empty {@link DataSource}.
	 */
	@Configuration(proxyBeanMethods = false)
	public static class BasicDataSourceConfiguration {

		// tag::configuration[]
		@Bean
		@ConfigurationProperties("app.datasource")
		public DataSource dataSource() {
			return DataSourceBuilder.create().build();
		}
		// end::configuration[]

	}

}
