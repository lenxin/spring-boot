package org.springframework.boot.autoconfigure.jdbc;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for multiple {@link DataSource}.
 *


 */
@Configuration(proxyBeanMethods = false)
class MultiDataSourceConfiguration {

	@Bean
	DataSource test1DataSource() {
		return new TestDataSource("test1");
	}

	@Bean
	DataSource test2DataSource() {
		return new TestDataSource("test2");
	}

}
