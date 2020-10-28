package org.springframework.boot.test.autoconfigure.data.jdbc;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

/**
 * Example {@link SpringBootApplication @SpringBootApplication} used with
 * {@link DataJdbcTest @DataJdbcTest} tests.
 *

 */
@SpringBootApplication
public class ExampleDataJdbcApplication {

	@Bean
	public DataSource dataSource() {
		return new EmbeddedDatabaseBuilder().generateUniqueName(true).setType(EmbeddedDatabaseType.HSQL).build();
	}

}
