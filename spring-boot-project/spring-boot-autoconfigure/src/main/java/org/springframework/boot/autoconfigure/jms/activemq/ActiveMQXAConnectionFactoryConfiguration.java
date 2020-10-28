package org.springframework.boot.autoconfigure.jms.activemq;

import java.util.stream.Collectors;

import javax.jms.ConnectionFactory;
import javax.transaction.TransactionManager;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQXAConnectionFactory;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.jms.XAConnectionFactoryWrapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * Configuration for ActiveMQ XA {@link ConnectionFactory}.
 *


 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(TransactionManager.class)
@ConditionalOnBean(XAConnectionFactoryWrapper.class)
@ConditionalOnMissingBean(ConnectionFactory.class)
class ActiveMQXAConnectionFactoryConfiguration {

	@Primary
	@Bean(name = { "jmsConnectionFactory", "xaJmsConnectionFactory" })
	ConnectionFactory jmsConnectionFactory(ActiveMQProperties properties,
			ObjectProvider<ActiveMQConnectionFactoryCustomizer> factoryCustomizers, XAConnectionFactoryWrapper wrapper)
			throws Exception {
		ActiveMQXAConnectionFactory connectionFactory = new ActiveMQConnectionFactoryFactory(properties,
				factoryCustomizers.orderedStream().collect(Collectors.toList()))
						.createConnectionFactory(ActiveMQXAConnectionFactory.class);
		return wrapper.wrapConnectionFactory(connectionFactory);
	}

	@Bean
	@ConditionalOnProperty(prefix = "spring.activemq.pool", name = "enabled", havingValue = "false",
			matchIfMissing = true)
	ActiveMQConnectionFactory nonXaJmsConnectionFactory(ActiveMQProperties properties,
			ObjectProvider<ActiveMQConnectionFactoryCustomizer> factoryCustomizers) {
		return new ActiveMQConnectionFactoryFactory(properties,
				factoryCustomizers.orderedStream().collect(Collectors.toList()))
						.createConnectionFactory(ActiveMQConnectionFactory.class);
	}

}
