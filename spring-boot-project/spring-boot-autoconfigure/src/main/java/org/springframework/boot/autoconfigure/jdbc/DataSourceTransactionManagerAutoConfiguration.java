package org.springframework.boot.autoconfigure.jdbc;

import javax.sql.DataSource;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.boot.autoconfigure.transaction.TransactionManagerCustomizers;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.JdbcTransactionManager;
import org.springframework.transaction.TransactionManager;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for {@link JdbcTransactionManager}.
 *




 * @since 1.0.0
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass({ JdbcTemplate.class, TransactionManager.class })
@AutoConfigureOrder(Ordered.LOWEST_PRECEDENCE)
@EnableConfigurationProperties(DataSourceProperties.class)
public class DataSourceTransactionManagerAutoConfiguration {

	@Configuration(proxyBeanMethods = false)
	@ConditionalOnSingleCandidate(DataSource.class)
	static class JdbcTransactionManagerConfiguration {

		@Bean
		@ConditionalOnMissingBean(TransactionManager.class)
		JdbcTransactionManager transactionManager(DataSource dataSource,
				ObjectProvider<TransactionManagerCustomizers> transactionManagerCustomizers) {
			JdbcTransactionManager transactionManager = new JdbcTransactionManager(dataSource);
			transactionManagerCustomizers.ifAvailable((customizers) -> customizers.customize(transactionManager));
			return transactionManager;
		}

	}

}
