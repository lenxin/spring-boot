package org.springframework.boot.autoconfigure.batch;

import org.junit.jupiter.api.Test;

import org.springframework.batch.core.configuration.annotation.BatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobOperator;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.autoconfigure.transaction.TransactionAutoConfiguration;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.boot.testsupport.classpath.ClassPathExclusions;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

/**
 * Tests for {@link BatchAutoConfiguration} when Spring JDBC is not on the classpath.
 *

 */
@ClassPathExclusions("spring-jdbc-*.jar")
class BatchAutoConfigurationWithoutJdbcTests {

	private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
			.withConfiguration(AutoConfigurations.of(BatchAutoConfiguration.class, TransactionAutoConfiguration.class))
			.withUserConfiguration(BatchConfiguration.class);

	@Test
	void whenThereIsNoJdbcOnTheClasspathThenComponentsAreStillAutoConfigured() {
		this.contextRunner.run((context) -> {
			assertThat(context).hasSingleBean(JobLauncherApplicationRunner.class);
			assertThat(context).hasSingleBean(JobExecutionExitCodeGenerator.class);
			assertThat(context).hasSingleBean(SimpleJobOperator.class);
		});
	}

	@Configuration
	@EnableBatchProcessing
	static class BatchConfiguration implements BatchConfigurer {

		@Override
		public JobRepository getJobRepository() throws Exception {
			return mock(JobRepository.class);
		}

		@Override
		public PlatformTransactionManager getTransactionManager() throws Exception {
			return mock(PlatformTransactionManager.class);
		}

		@Override
		public JobLauncher getJobLauncher() throws Exception {
			return mock(JobLauncher.class);
		}

		@Override
		public JobExplorer getJobExplorer() throws Exception {
			return mock(JobExplorer.class);
		}

	}

}
