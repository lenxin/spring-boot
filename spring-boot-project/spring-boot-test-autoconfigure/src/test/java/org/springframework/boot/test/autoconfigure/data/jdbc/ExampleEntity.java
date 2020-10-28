package org.springframework.boot.test.autoconfigure.data.jdbc;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

/**
 * Example entity used with {@link DataJdbcTest @DataJdbcTest} tests.
 *

 */
@Table("EXAMPLE_ENTITY")
public class ExampleEntity {

	@Id
	private Long id;

	private String name;

	private String reference;

	public ExampleEntity(String name, String reference) {
		this.name = name;
		this.reference = reference;
	}

	public String getName() {
		return this.name;
	}

	public String getReference() {
		return this.reference;
	}

}
