package org.springframework.boot.actuate.autoconfigure.cassandra;

import com.datastax.oss.driver.api.core.CqlSession;
import org.junit.jupiter.api.Test;

import org.springframework.boot.actuate.autoconfigure.health.HealthContributorAutoConfiguration;
import org.springframework.boot.actuate.cassandra.CassandraDriverHealthIndicator;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.FilteredClassLoader;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.data.cassandra.core.CassandraOperations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

/**
 * Tests for {@link CassandraHealthContributorAutoConfiguration}.
 *


 */
@SuppressWarnings("deprecation")
class CassandraHealthContributorAutoConfigurationTests {

	private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
			.withConfiguration(AutoConfigurations.of(CassandraHealthContributorAutoConfiguration.class,
					HealthContributorAutoConfiguration.class));

	@Test
	void runWithoutCqlSessionOrCassandraOperationsShouldNotCreateIndicator() {
		this.contextRunner.run((context) -> assertThat(context).doesNotHaveBean("cassandraHealthContributor")
				.doesNotHaveBean(org.springframework.boot.actuate.cassandra.CassandraHealthIndicator.class)
				.doesNotHaveBean(CassandraDriverHealthIndicator.class));
	}

	@Test
	void runWithCqlSessionOnlyShouldCreateDriverIndicator() {
		this.contextRunner.withBean(CqlSession.class, () -> mock(CqlSession.class))
				.run((context) -> assertThat(context).hasSingleBean(CassandraDriverHealthIndicator.class)
						.doesNotHaveBean(org.springframework.boot.actuate.cassandra.CassandraHealthIndicator.class));
	}

	@Test
	void runWithCassandraOperationsOnlyShouldCreateRegularIndicator() {
		this.contextRunner.withBean(CassandraOperations.class, () -> mock(CassandraOperations.class))
				.run((context) -> assertThat(context)
						.hasSingleBean(org.springframework.boot.actuate.cassandra.CassandraHealthIndicator.class)
						.doesNotHaveBean(CassandraDriverHealthIndicator.class));
	}

	@Test
	void runWithCqlSessionAndCassandraOperationsShouldCreateDriverIndicator() {
		this.contextRunner.withBean(CqlSession.class, () -> mock(CqlSession.class))
				.withBean(CassandraOperations.class, () -> mock(CassandraOperations.class))
				.run((context) -> assertThat(context).hasSingleBean(CassandraDriverHealthIndicator.class)
						.doesNotHaveBean(org.springframework.boot.actuate.cassandra.CassandraHealthIndicator.class));
	}

	@Test
	void runWithCqlSessionAndSpringDataAbsentShouldCreateDriverIndicator() {
		this.contextRunner.withBean(CqlSession.class, () -> mock(CqlSession.class))
				.withClassLoader(new FilteredClassLoader("org.springframework.data"))
				.run((context) -> assertThat(context).hasSingleBean(CassandraDriverHealthIndicator.class)
						.doesNotHaveBean(org.springframework.boot.actuate.cassandra.CassandraHealthIndicator.class));
	}

	@Test
	void runWhenDisabledShouldNotCreateIndicator() {
		this.contextRunner.withBean(CqlSession.class, () -> mock(CqlSession.class))
				.withBean(CassandraOperations.class, () -> mock(CassandraOperations.class))
				.withPropertyValues("management.health.cassandra.enabled:false")
				.run((context) -> assertThat(context).doesNotHaveBean("cassandraHealthContributor")
						.doesNotHaveBean(org.springframework.boot.actuate.cassandra.CassandraHealthIndicator.class)
						.doesNotHaveBean(CassandraDriverHealthIndicator.class));
	}

}
