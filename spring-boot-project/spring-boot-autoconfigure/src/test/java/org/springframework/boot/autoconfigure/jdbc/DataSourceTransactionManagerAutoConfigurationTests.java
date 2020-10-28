package org.springframework.boot.autoconfigure.jdbc;

import java.util.UUID;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;

import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.autoconfigure.transaction.TransactionAutoConfiguration;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.jdbc.support.JdbcTransactionManager;
import org.springframework.transaction.TransactionManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

/**
 * Tests for {@link DataSourceTransactionManagerAutoConfiguration}.
 *



 */
class DataSourceTransactionManagerAutoConfigurationTests {

	private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
			.withConfiguration(AutoConfigurations.of(TransactionAutoConfiguration.class,
					DataSourceTransactionManagerAutoConfiguration.class))
			.withPropertyValues("spring.datasource.url:jdbc:hsqldb:mem:test-" + UUID.randomUUID());

	@Test
	void transactionManagerWithoutDataSourceIsNotConfigured() {
		this.contextRunner.run((context) -> assertThat(context).doesNotHaveBean(TransactionManager.class));
	}

	@Test
	void transactionManagerWithExistingDataSourceIsConfigured() {
		this.contextRunner.withConfiguration(AutoConfigurations.of(DataSourceAutoConfiguration.class))
				.run((context) -> {
					assertThat(context).hasSingleBean(TransactionManager.class)
							.hasSingleBean(JdbcTransactionManager.class);
					assertThat(context.getBean(JdbcTransactionManager.class).getDataSource())
							.isSameAs(context.getBean(DataSource.class));
				});
	}

	@Test
	void transactionManagerWithCustomizationIsConfigured() {
		this.contextRunner.withConfiguration(AutoConfigurations.of(DataSourceAutoConfiguration.class))
				.withPropertyValues("spring.transaction.default-timeout=1m",
						"spring.transaction.rollback-on-commit-failure=true")
				.run((context) -> {
					assertThat(context).hasSingleBean(TransactionManager.class)
							.hasSingleBean(JdbcTransactionManager.class);
					JdbcTransactionManager transactionManager = context.getBean(JdbcTransactionManager.class);
					assertThat(transactionManager.getDefaultTimeout()).isEqualTo(60);
					assertThat(transactionManager.isRollbackOnCommitFailure()).isTrue();
				});
	}

	@Test
	void transactionManagerWithExistingTransactionManagerIsNotOverridden() {
		this.contextRunner
				.withBean("myTransactionManager", TransactionManager.class, () -> mock(TransactionManager.class))
				.run((context) -> assertThat(context).hasSingleBean(TransactionManager.class)
						.hasBean("myTransactionManager"));
	}

	@Test
	void transactionWithMultipleDataSourcesIsNotConfigured() {
		this.contextRunner.withUserConfiguration(MultiDataSourceConfiguration.class)
				.run((context) -> assertThat(context).doesNotHaveBean(TransactionManager.class));
	}

	@Test
	void transactionWithMultipleDataSourcesAndPrimaryCandidateIsConfigured() {
		this.contextRunner.withUserConfiguration(MultiDataSourceUsingPrimaryConfiguration.class).run((context) -> {
			assertThat(context).hasSingleBean(TransactionManager.class).hasSingleBean(JdbcTransactionManager.class);
			assertThat(context.getBean(JdbcTransactionManager.class).getDataSource())
					.isSameAs(context.getBean("test1DataSource"));
		});
	}

}
