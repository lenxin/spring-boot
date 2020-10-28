package org.springframework.boot.test.autoconfigure.orm.jpa;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Example entity used with {@link DataJpaTest @DataJpaTest} tests.
 *

 */
@Entity
public class ExampleEntity {

	@Id
	@GeneratedValue
	private Long id;

	private String name;

	private String reference;

	protected ExampleEntity() {
	}

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
