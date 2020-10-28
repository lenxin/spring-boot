package org.springframework.boot.autoconfigure.session;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.session.SessionRepository;
import org.springframework.session.data.mongo.MongoIndexedSessionRepository;
import org.springframework.session.data.mongo.config.annotation.web.http.MongoHttpSessionConfiguration;

/**
 * Mongo-backed session configuration.
 *


 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass({ MongoOperations.class, MongoIndexedSessionRepository.class })
@ConditionalOnMissingBean(SessionRepository.class)
@ConditionalOnBean(MongoOperations.class)
@Conditional(ServletSessionCondition.class)
@EnableConfigurationProperties(MongoSessionProperties.class)
class MongoSessionConfiguration {

	@Configuration(proxyBeanMethods = false)
	public static class SpringBootMongoHttpSessionConfiguration extends MongoHttpSessionConfiguration {

		@Autowired
		public void customize(SessionProperties sessionProperties, MongoSessionProperties mongoSessionProperties,
				ServerProperties serverProperties) {
			Duration timeout = sessionProperties
					.determineTimeout(() -> serverProperties.getServlet().getSession().getTimeout());
			if (timeout != null) {
				setMaxInactiveIntervalInSeconds((int) timeout.getSeconds());
			}
			setCollectionName(mongoSessionProperties.getCollectionName());
		}

	}

}
