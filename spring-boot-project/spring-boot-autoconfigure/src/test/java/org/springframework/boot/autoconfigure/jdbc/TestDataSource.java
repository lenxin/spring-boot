package org.springframework.boot.autoconfigure.jdbc;

import java.util.UUID;

import org.apache.commons.dbcp2.BasicDataSource;

/**
 * {@link BasicDataSource} used for testing.
 *



 */
public class TestDataSource extends BasicDataSource {

	/**
	 * Create an in-memory database with the specified name.
	 * @param name the name of the database
	 */
	public TestDataSource(String name) {
		setDriverClassName("org.hsqldb.jdbcDriver");
		setUrl("jdbc:hsqldb:mem:" + name);
		setUsername("sa");
	}

	/**
	 * Create an in-memory database with a random name.
	 */
	public TestDataSource() {
		this(UUID.randomUUID().toString());
	}

}
