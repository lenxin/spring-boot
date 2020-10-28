package org.springframework.boot.autoconfigure.mongo;

import java.util.stream.Collectors;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for Mongo.
 *






 * @since 1.0.0
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(MongoClient.class)
@EnableConfigurationProperties(MongoProperties.class)
@ConditionalOnMissingBean(type = "org.springframework.data.mongodb.MongoDatabaseFactory")
public class MongoAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean(MongoClient.class)
	public MongoClient mongo(ObjectProvider<MongoClientSettingsBuilderCustomizer> builderCustomizers,
			MongoClientSettings settings) {
		return new MongoClientFactory(builderCustomizers.orderedStream().collect(Collectors.toList()))
				.createMongoClient(settings);
	}

	@Configuration(proxyBeanMethods = false)
	@ConditionalOnMissingBean(MongoClientSettings.class)
	static class MongoClientSettingsConfiguration {

		@Bean
		MongoClientSettings mongoClientSettings() {
			return MongoClientSettings.builder().build();
		}

		@Bean
		MongoPropertiesClientSettingsBuilderCustomizer mongoPropertiesCustomizer(MongoProperties properties,
				Environment environment) {
			return new MongoPropertiesClientSettingsBuilderCustomizer(properties, environment);
		}

	}

}
