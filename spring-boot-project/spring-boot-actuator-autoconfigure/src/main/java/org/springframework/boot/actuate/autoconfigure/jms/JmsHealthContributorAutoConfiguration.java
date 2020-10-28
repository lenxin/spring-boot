package org.springframework.boot.actuate.autoconfigure.jms;

import java.util.Map;

import javax.jms.ConnectionFactory;

import org.springframework.boot.actuate.autoconfigure.health.CompositeHealthContributorConfiguration;
import org.springframework.boot.actuate.autoconfigure.health.ConditionalOnEnabledHealthIndicator;
import org.springframework.boot.actuate.health.HealthContributor;
import org.springframework.boot.actuate.jms.JmsHealthIndicator;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jms.activemq.ActiveMQAutoConfiguration;
import org.springframework.boot.autoconfigure.jms.artemis.ArtemisAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for {@link JmsHealthIndicator}.
 *

 * @since 2.0.0
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(ConnectionFactory.class)
@ConditionalOnBean(ConnectionFactory.class)
@ConditionalOnEnabledHealthIndicator("jms")
@AutoConfigureAfter({ ActiveMQAutoConfiguration.class, ArtemisAutoConfiguration.class })
public class JmsHealthContributorAutoConfiguration
		extends CompositeHealthContributorConfiguration<JmsHealthIndicator, ConnectionFactory> {

	@Bean
	@ConditionalOnMissingBean(name = { "jmsHealthIndicator", "jmsHealthContributor" })
	public HealthContributor jmsHealthContributor(Map<String, ConnectionFactory> connectionFactories) {
		return createContributor(connectionFactories);
	}

}
