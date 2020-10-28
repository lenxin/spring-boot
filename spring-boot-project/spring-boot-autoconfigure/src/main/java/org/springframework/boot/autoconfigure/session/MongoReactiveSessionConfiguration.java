package org.springframework.boot.autoconfigure.session;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.session.ReactiveSessionRepository;
import org.springframework.session.data.mongo.ReactiveMongoSessionRepository;
import org.springframework.session.data.mongo.config.annotation.web.reactive.ReactiveMongoWebSessionConfiguration;

/**
 * Mongo-backed reactive session configuration.
 *

 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass({ ReactiveMongoOperations.class, ReactiveMongoSessionRepository.class })
@ConditionalOnMissingBean(ReactiveSessionRepository.class)
@ConditionalOnBean(ReactiveMongoOperations.class)
@Conditional(ReactiveSessionCondition.class)
@EnableConfigurationProperties(MongoSessionProperties.class)
class MongoReactiveSessionConfiguration {

	@Configuration(proxyBeanMethods = false)
	static class SpringBootReactiveMongoWebSessionConfiguration extends ReactiveMongoWebSessionConfiguration {

		@Autowired
		void customize(SessionProperties sessionProperties, MongoSessionProperties mongoSessionProperties) {
			Duration timeout = sessionProperties.getTimeout();
			if (timeout != null) {
				setMaxInactiveIntervalInSeconds((int) timeout.getSeconds());
			}
			setCollectionName(mongoSessionProperties.getCollectionName());
		}

	}

}
