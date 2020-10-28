package org.springframework.boot.test.autoconfigure.data.redis;

import org.springframework.data.repository.CrudRepository;

/**
 * Example repository used with {@link DataRedisTest @DataRedisTest} tests.
 *

 */
interface ExampleRepository extends CrudRepository<PersonHash, String> {

}
