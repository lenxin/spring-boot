package org.springframework.boot.test.autoconfigure.data.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Example repository used with {@link DataMongoTest @DataMongoTest} tests.
 *

 */
interface ExampleRepository extends MongoRepository<ExampleDocument, String> {

}
