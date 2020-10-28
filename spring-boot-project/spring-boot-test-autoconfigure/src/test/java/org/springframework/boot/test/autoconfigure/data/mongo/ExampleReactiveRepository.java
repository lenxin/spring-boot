package org.springframework.boot.test.autoconfigure.data.mongo;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

/**
 * Example reactive repository used with {@link DataMongoTest @DataMongoTest} tests.
 *

 */
interface ExampleReactiveRepository extends ReactiveMongoRepository<ExampleDocument, String> {

}
