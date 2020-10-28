package org.springframework.boot.actuate.autoconfigure.r2dbc;

import java.util.Map;

import io.r2dbc.spi.ConnectionFactory;

import org.springframework.boot.actuate.autoconfigure.health.CompositeReactiveHealthContributorConfiguration;
import org.springframework.boot.actuate.autoconfigure.health.ConditionalOnEnabledHealthIndicator;
import org.springframework.boot.actuate.health.ReactiveHealthContributor;
import org.springframework.boot.actuate.r2dbc.ConnectionFactoryHealthIndicator;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.r2dbc.R2dbcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for
 * {@link ConnectionFactoryHealthIndicator}.
 *

 * @since 2.3.0
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(ConnectionFactory.class)
@ConditionalOnBean(ConnectionFactory.class)
@ConditionalOnEnabledHealthIndicator("r2dbc")
@AutoConfigureAfter(R2dbcAutoConfiguration.class)
public class ConnectionFactoryHealthContributorAutoConfiguration
		extends CompositeReactiveHealthContributorConfiguration<ConnectionFactoryHealthIndicator, ConnectionFactory> {

	private final Map<String, ConnectionFactory> connectionFactory;

	ConnectionFactoryHealthContributorAutoConfiguration(Map<String, ConnectionFactory> connectionFactory) {
		this.connectionFactory = connectionFactory;
	}

	@Bean
	@ConditionalOnMissingBean(name = { "r2dbcHealthIndicator", "r2dbcHealthContributor" })
	public ReactiveHealthContributor r2dbcHealthContributor() {
		return createContributor(this.connectionFactory);
	}

}
