package org.springframework.boot.autoconfigure.batch;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.batch.core.configuration.annotation.BatchConfigurer;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.transaction.TransactionManagerCustomizers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * Provide a {@link BatchConfigurer} according to the current environment.
 *

 */
@ConditionalOnClass(PlatformTransactionManager.class)
@ConditionalOnBean(DataSource.class)
@ConditionalOnMissingBean(BatchConfigurer.class)
@Configuration(proxyBeanMethods = false)
class BatchConfigurerConfiguration {

	@Configuration(proxyBeanMethods = false)
	@ConditionalOnMissingBean(name = "entityManagerFactory")
	static class JdbcBatchConfiguration {

		@Bean
		BasicBatchConfigurer batchConfigurer(BatchProperties properties, DataSource dataSource,
				@BatchDataSource ObjectProvider<DataSource> batchDataSource,
				ObjectProvider<TransactionManagerCustomizers> transactionManagerCustomizers) {
			return new BasicBatchConfigurer(properties, batchDataSource.getIfAvailable(() -> dataSource),
					transactionManagerCustomizers.getIfAvailable());
		}

	}

	@Configuration(proxyBeanMethods = false)
	@ConditionalOnClass(EntityManagerFactory.class)
	@ConditionalOnBean(name = "entityManagerFactory")
	static class JpaBatchConfiguration {

		@Bean
		JpaBatchConfigurer batchConfigurer(BatchProperties properties, DataSource dataSource,
				@BatchDataSource ObjectProvider<DataSource> batchDataSource,
				ObjectProvider<TransactionManagerCustomizers> transactionManagerCustomizers,
				EntityManagerFactory entityManagerFactory) {
			return new JpaBatchConfigurer(properties, batchDataSource.getIfAvailable(() -> dataSource),
					transactionManagerCustomizers.getIfAvailable(), entityManagerFactory);
		}

	}

}
