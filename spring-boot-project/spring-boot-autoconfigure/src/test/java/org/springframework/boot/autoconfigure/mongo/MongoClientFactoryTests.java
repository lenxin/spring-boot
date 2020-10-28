package org.springframework.boot.autoconfigure.mongo;

import java.util.List;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;

import org.springframework.test.util.ReflectionTestUtils;

/**
 * Tests for {@link MongoClientFactory}.
 *





 */
class MongoClientFactoryTests extends MongoClientFactorySupportTests<MongoClient> {

	@Override
	protected MongoClient createMongoClient(List<MongoClientSettingsBuilderCustomizer> customizers,
			MongoClientSettings settings) {
		return new MongoClientFactory(customizers).createMongoClient(settings);
	}

	@Override
	protected MongoClientSettings getClientSettings(MongoClient client) {
		return (MongoClientSettings) ReflectionTestUtils.getField(client, "settings");
	}

}
