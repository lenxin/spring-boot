package org.springframework.boot.test.autoconfigure.data.cassandra;

import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.stereotype.Service;

/**
 * Example service used with {@link DataCassandraTest @DataCassandraTest} tests.
 *

 */
@Service
public class ExampleService {

	private final CassandraTemplate cassandraTemplate;

	public ExampleService(CassandraTemplate cassandraTemplate) {
		this.cassandraTemplate = cassandraTemplate;
	}

	public boolean hasRecord(ExampleEntity entity) {
		return this.cassandraTemplate.exists(entity.getId(), ExampleEntity.class);
	}

}
