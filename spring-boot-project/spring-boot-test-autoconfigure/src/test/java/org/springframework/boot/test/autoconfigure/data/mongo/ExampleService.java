package org.springframework.boot.test.autoconfigure.data.mongo;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

/**
 * Example service used with {@link DataMongoTest @DataMongoTest} tests.
 *

 */
@Service
public class ExampleService {

	private final MongoTemplate mongoTemplate;

	public ExampleService(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	public boolean hasCollection(String collectionName) {
		return this.mongoTemplate.collectionExists(collectionName);
	}

}
