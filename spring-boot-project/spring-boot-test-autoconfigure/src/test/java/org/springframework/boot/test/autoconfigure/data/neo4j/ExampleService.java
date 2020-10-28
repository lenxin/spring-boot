package org.springframework.boot.test.autoconfigure.data.neo4j;

import org.springframework.data.neo4j.core.Neo4jTemplate;
import org.springframework.stereotype.Service;

/**
 * Example service used with {@link DataNeo4jTest @DataNeo4jTest} tests.
 *


 */
@Service
public class ExampleService {

	private final Neo4jTemplate neo4jTemplate;

	public ExampleService(Neo4jTemplate neo4jTemplate) {
		this.neo4jTemplate = neo4jTemplate;
	}

	public boolean hasNode(Class<?> clazz) {
		return this.neo4jTemplate.count(clazz) == 1;
	}

}
