package org.springframework.boot.autoconfigure.jdbc;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * Configuration for multiple {@link DataSource} (one being {@code @Primary}.
 *


 */
@Configuration(proxyBeanMethods = false)
class MultiDataSourceUsingPrimaryConfiguration {

	@Bean
	@Primary
	DataSource test1DataSource() {
		return new TestDataSource("test1");
	}

	@Bean
	DataSource test2DataSource() {
		return new TestDataSource("test2");
	}

}
