package org.springframework.boot.autoconfigure.mongo;

import java.util.List;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;

import org.springframework.core.env.Environment;

/**
 * A factory for a reactive {@link MongoClient}.
 *



 * @since 2.0.0
 */
public class ReactiveMongoClientFactory extends MongoClientFactorySupport<MongoClient> {

	/**
	 * Construct a factory for creating a {@link MongoClient}.
	 * @param properties configuration properties
	 * @param environment a Spring {@link Environment} containing configuration properties
	 * @param builderCustomizers a list of configuration settings customizers
	 * @deprecated since 2.4.0 in favor of {@link #ReactiveMongoClientFactory(List)}
	 */
	@Deprecated
	public ReactiveMongoClientFactory(MongoProperties properties, Environment environment,
			List<MongoClientSettingsBuilderCustomizer> builderCustomizers) {
		super(properties, environment, builderCustomizers, MongoClients::create);
	}

	/**
	 * Construct a factory for creating a {@link MongoClient}.
	 * @param builderCustomizers a list of configuration settings customizers
	 */
	public ReactiveMongoClientFactory(List<MongoClientSettingsBuilderCustomizer> builderCustomizers) {
		super(builderCustomizers, MongoClients::create);
	}

}
