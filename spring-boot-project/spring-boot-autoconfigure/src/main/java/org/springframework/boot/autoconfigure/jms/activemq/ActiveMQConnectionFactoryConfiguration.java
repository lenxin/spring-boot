package org.springframework.boot.autoconfigure.jms.activemq;

import java.util.stream.Collectors;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.commons.pool2.PooledObject;
import org.messaginghub.pooled.jms.JmsPoolConnectionFactory;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jms.JmsPoolConnectionFactoryFactory;
import org.springframework.boot.autoconfigure.jms.JmsProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.connection.CachingConnectionFactory;

/**
 * Configuration for ActiveMQ {@link ConnectionFactory}.
 *





 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnMissingBean(ConnectionFactory.class)
class ActiveMQConnectionFactoryConfiguration {

	@Configuration(proxyBeanMethods = false)
	@ConditionalOnProperty(prefix = "spring.activemq.pool", name = "enabled", havingValue = "false",
			matchIfMissing = true)
	static class SimpleConnectionFactoryConfiguration {

		@Bean
		@ConditionalOnProperty(prefix = "spring.jms.cache", name = "enabled", havingValue = "false")
		ActiveMQConnectionFactory jmsConnectionFactory(ActiveMQProperties properties,
				ObjectProvider<ActiveMQConnectionFactoryCustomizer> factoryCustomizers) {
			return createJmsConnectionFactory(properties, factoryCustomizers);
		}

		private static ActiveMQConnectionFactory createJmsConnectionFactory(ActiveMQProperties properties,
				ObjectProvider<ActiveMQConnectionFactoryCustomizer> factoryCustomizers) {
			return new ActiveMQConnectionFactoryFactory(properties,
					factoryCustomizers.orderedStream().collect(Collectors.toList()))
							.createConnectionFactory(ActiveMQConnectionFactory.class);
		}

		@Configuration(proxyBeanMethods = false)
		@ConditionalOnClass(CachingConnectionFactory.class)
		@ConditionalOnProperty(prefix = "spring.jms.cache", name = "enabled", havingValue = "true",
				matchIfMissing = true)
		static class CachingConnectionFactoryConfiguration {

			@Bean
			CachingConnectionFactory jmsConnectionFactory(JmsProperties jmsProperties, ActiveMQProperties properties,
					ObjectProvider<ActiveMQConnectionFactoryCustomizer> factoryCustomizers) {
				JmsProperties.Cache cacheProperties = jmsProperties.getCache();
				CachingConnectionFactory connectionFactory = new CachingConnectionFactory(
						createJmsConnectionFactory(properties, factoryCustomizers));
				connectionFactory.setCacheConsumers(cacheProperties.isConsumers());
				connectionFactory.setCacheProducers(cacheProperties.isProducers());
				connectionFactory.setSessionCacheSize(cacheProperties.getSessionCacheSize());
				return connectionFactory;
			}

		}

	}

	@Configuration(proxyBeanMethods = false)
	@ConditionalOnClass({ JmsPoolConnectionFactory.class, PooledObject.class })
	static class PooledConnectionFactoryConfiguration {

		@Bean(destroyMethod = "stop")
		@ConditionalOnProperty(prefix = "spring.activemq.pool", name = "enabled", havingValue = "true")
		JmsPoolConnectionFactory jmsConnectionFactory(ActiveMQProperties properties,
				ObjectProvider<ActiveMQConnectionFactoryCustomizer> factoryCustomizers) {
			ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactoryFactory(properties,
					factoryCustomizers.orderedStream().collect(Collectors.toList()))
							.createConnectionFactory(ActiveMQConnectionFactory.class);
			return new JmsPoolConnectionFactoryFactory(properties.getPool())
					.createPooledConnectionFactory(connectionFactory);
		}

	}

}
