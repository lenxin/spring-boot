package org.springframework.boot.autoconfigure.data.neo4j;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration properties for Spring Data Neo4j.
 *

 * @since 2.4.0
 */
@ConfigurationProperties(prefix = "spring.data.neo4j")
public class Neo4jDataProperties {

	/**
	 * Database name to use. By default, the server decides the default database to use.
	 */
	private String database;

	public String getDatabase() {
		return this.database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

}
