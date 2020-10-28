package org.springframework.boot.autoconfigure.mongo;

import java.util.List;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

import org.springframework.core.env.Environment;

/**
 * A factory for a blocking {@link MongoClient}.
 *









 * @since 2.0.0
 */
public class MongoClientFactory extends MongoClientFactorySupport<MongoClient> {

	/**
	 * Construct a factory for creating a blocking {@link MongoClient}.
	 * @param properties configuration properties
	 * @param environment a Spring {@link Environment} containing configuration properties
	 * @deprecated since 2.3.0 in favor of {@link #MongoClientFactory(List)}
	 */
	@Deprecated
	public MongoClientFactory(MongoProperties properties, Environment environment) {
		this(null);
	}

	/**
	 * Construct a factory for creating a blocking {@link MongoClient}.
	 * @param properties configuration properties
	 * @param environment a Spring {@link Environment} containing configuration properties
	 * @param builderCustomizers a list of configuration settings customizers
	 * @deprecated since 2.4.0 in favor of {@link #MongoClientFactory(List)}
	 */
	@Deprecated
	public MongoClientFactory(MongoProperties properties, Environment environment,
			List<MongoClientSettingsBuilderCustomizer> builderCustomizers) {
		this(builderCustomizers);
	}

	/**
	 * Construct a factory for creating a blocking {@link MongoClient}.
	 * @param builderCustomizers a list of configuration settings customizers
	 */
	public MongoClientFactory(List<MongoClientSettingsBuilderCustomizer> builderCustomizers) {
		super(builderCustomizers, MongoClients::create);
	}

}
