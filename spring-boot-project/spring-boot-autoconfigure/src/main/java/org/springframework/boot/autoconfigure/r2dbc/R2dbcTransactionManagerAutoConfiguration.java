package org.springframework.boot.autoconfigure.r2dbc;

import io.r2dbc.spi.ConnectionFactory;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.boot.autoconfigure.transaction.TransactionAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.r2dbc.connection.R2dbcTransactionManager;
import org.springframework.transaction.ReactiveTransactionManager;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for {@link R2dbcTransactionManager}.
 *

 * @since 2.3.0
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass({ R2dbcTransactionManager.class, ReactiveTransactionManager.class })
@ConditionalOnSingleCandidate(ConnectionFactory.class)
@AutoConfigureOrder(Ordered.LOWEST_PRECEDENCE)
@AutoConfigureBefore(TransactionAutoConfiguration.class)
public class R2dbcTransactionManagerAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean(ReactiveTransactionManager.class)
	public R2dbcTransactionManager connectionFactoryTransactionManager(ConnectionFactory connectionFactory) {
		return new R2dbcTransactionManager(connectionFactory);
	}

}
