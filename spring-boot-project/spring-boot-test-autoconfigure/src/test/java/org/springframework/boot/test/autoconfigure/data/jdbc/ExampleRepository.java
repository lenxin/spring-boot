package org.springframework.boot.test.autoconfigure.data.jdbc;

import org.springframework.data.repository.CrudRepository;

/**
 * Example repository used with {@link DataJdbcTest @DataJdbcTest} tests.
 *

 */
interface ExampleRepository extends CrudRepository<ExampleEntity, Long> {

}
