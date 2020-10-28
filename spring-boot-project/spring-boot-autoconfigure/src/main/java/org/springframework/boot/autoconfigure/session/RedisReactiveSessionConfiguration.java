package org.springframework.boot.autoconfigure.session;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.session.ReactiveSessionRepository;
import org.springframework.session.data.redis.ReactiveRedisSessionRepository;
import org.springframework.session.data.redis.config.annotation.web.server.RedisWebSessionConfiguration;

/**
 * Redis-backed reactive session configuration.
 *

 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass({ ReactiveRedisConnectionFactory.class, ReactiveRedisSessionRepository.class })
@ConditionalOnMissingBean(ReactiveSessionRepository.class)
@ConditionalOnBean(ReactiveRedisConnectionFactory.class)
@Conditional(ReactiveSessionCondition.class)
@EnableConfigurationProperties(RedisSessionProperties.class)
class RedisReactiveSessionConfiguration {

	@Configuration(proxyBeanMethods = false)
	static class SpringBootRedisWebSessionConfiguration extends RedisWebSessionConfiguration {

		@Autowired
		void customize(SessionProperties sessionProperties, RedisSessionProperties redisSessionProperties) {
			Duration timeout = sessionProperties.getTimeout();
			if (timeout != null) {
				setMaxInactiveIntervalInSeconds((int) timeout.getSeconds());
			}
			setRedisNamespace(redisSessionProperties.getNamespace());
			setSaveMode(redisSessionProperties.getSaveMode());
		}

	}

}
