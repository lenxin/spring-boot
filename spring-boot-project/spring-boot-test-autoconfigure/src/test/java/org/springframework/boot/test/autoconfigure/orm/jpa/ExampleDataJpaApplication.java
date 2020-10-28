package org.springframework.boot.test.autoconfigure.orm.jpa;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

/**
 * Example {@link SpringBootApplication @SpringBootApplication} used with
 * {@link DataJpaTest @DataJpaTest} tests.
 *

 */
@SpringBootApplication
public class ExampleDataJpaApplication {

	@Bean
	public DataSource dataSource() {
		return new EmbeddedDatabaseBuilder().generateUniqueName(true).setType(EmbeddedDatabaseType.HSQL).build();
	}

}
