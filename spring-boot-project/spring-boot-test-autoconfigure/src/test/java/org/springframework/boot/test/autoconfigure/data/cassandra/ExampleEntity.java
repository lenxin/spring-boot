package org.springframework.boot.test.autoconfigure.data.cassandra;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

/**
 * Example graph used with {@link DataCassandraTest @DataCassandraTest} tests.
 *

 */
@Table
public class ExampleEntity {

	@PrimaryKey
	private String id;

	private String description;

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
