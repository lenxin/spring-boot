package org.springframework.boot.autoconfigure.mongo;

import java.util.List;

import com.mongodb.MongoClientSettings;
import com.mongodb.internal.async.client.AsyncMongoClient;
import com.mongodb.reactivestreams.client.MongoClient;

import org.springframework.test.util.ReflectionTestUtils;

/**
 * Tests for {@link ReactiveMongoClientFactory}.
 *



 */
class ReactiveMongoClientFactoryTests extends MongoClientFactorySupportTests<MongoClient> {

	@Override
	protected MongoClient createMongoClient(List<MongoClientSettingsBuilderCustomizer> customizers,
			MongoClientSettings settings) {
		return new ReactiveMongoClientFactory(customizers).createMongoClient(settings);
	}

	@Override
	protected MongoClientSettings getClientSettings(MongoClient client) {
		AsyncMongoClient wrapped = (AsyncMongoClient) ReflectionTestUtils.getField(client, "wrapped");
		return (MongoClientSettings) ReflectionTestUtils.getField(wrapped, "settings");
	}

}
