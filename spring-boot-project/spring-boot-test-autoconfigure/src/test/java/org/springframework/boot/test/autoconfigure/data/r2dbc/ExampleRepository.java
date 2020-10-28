package org.springframework.boot.test.autoconfigure.data.r2dbc;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

/**
 * Example {@link ReactiveCrudRepository} used with {@link DataR2dbcTest} tests.
 *

 */
public interface ExampleRepository extends ReactiveCrudRepository<Example, String> {

}
