package org.springframework.boot.test.autoconfigure.jdbc;

/**
 * Example entity used with {@link JdbcTest @JdbcTest} tests.
 *

 */
public class ExampleEntity {

	private final int id;

	private String name;

	public ExampleEntity(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public ExampleEntity(int id) {
		this(id, null);
	}

	public int getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
