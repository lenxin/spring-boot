package org.springframework.boot.autoconfigure.data.mongo;

import com.mongodb.client.MongoClient;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoDatabaseFactorySupport;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

/**
 * Configuration for a {@link MongoDatabaseFactory}.
 *


 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnMissingBean(MongoDatabaseFactory.class)
@ConditionalOnSingleCandidate(MongoClient.class)
class MongoDatabaseFactoryConfiguration {

	@Bean
	MongoDatabaseFactorySupport<?> mongoDatabaseFactory(MongoClient mongoClient, MongoProperties properties) {
		return new SimpleMongoClientDatabaseFactory(mongoClient, properties.getMongoClientDatabase());
	}

}
