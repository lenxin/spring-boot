package org.springframework.boot.test.autoconfigure.data.cassandra;

import org.springframework.data.cassandra.repository.CassandraRepository;

/**
 * Example repository used with {@link DataCassandraTest @DataCassandraTest} tests.
 *

 */
interface ExampleRepository extends CassandraRepository<ExampleEntity, String> {

}
