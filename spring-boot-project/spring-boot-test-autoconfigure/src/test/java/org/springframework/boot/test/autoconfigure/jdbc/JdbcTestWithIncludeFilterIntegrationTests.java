package org.springframework.boot.test.autoconfigure.jdbc;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration test with custom include filter for {@link JdbcTest @JdbcTest}.
 *

 */
@JdbcTest(includeFilters = @Filter(Repository.class))
@TestPropertySource(
		properties = "spring.datasource.schema=classpath:org/springframework/boot/test/autoconfigure/jdbc/schema.sql")
class JdbcTestWithIncludeFilterIntegrationTests {

	@Autowired
	private ExampleRepository repository;

	@Test
	void testRepository() {
		this.repository.save(new ExampleEntity(42, "Smith"));
		ExampleEntity entity = this.repository.findById(42);
		assertThat(entity).isNotNull();
		assertThat(entity.getName()).isEqualTo("Smith");
	}

}
