package org.springframework.boot.actuate.autoconfigure.cassandra;

import java.util.Map;

import com.datastax.oss.driver.api.core.CqlSession;

import org.springframework.boot.actuate.autoconfigure.health.CompositeHealthContributorConfiguration;
import org.springframework.boot.actuate.autoconfigure.health.CompositeReactiveHealthContributorConfiguration;
import org.springframework.boot.actuate.cassandra.CassandraDriverHealthIndicator;
import org.springframework.boot.actuate.cassandra.CassandraDriverReactiveHealthIndicator;
import org.springframework.boot.actuate.health.HealthContributor;
import org.springframework.boot.actuate.health.ReactiveHealthContributor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.ReactiveCassandraOperations;

/**
 * Health contributor options for Cassandra.
 *

 */
class CassandraHealthContributorConfigurations {

	@Configuration(proxyBeanMethods = false)
	@ConditionalOnBean(CqlSession.class)
	static class CassandraDriverConfiguration
			extends CompositeHealthContributorConfiguration<CassandraDriverHealthIndicator, CqlSession> {

		@Bean
		@ConditionalOnMissingBean(name = { "cassandraHealthIndicator", "cassandraHealthContributor" })
		HealthContributor cassandraHealthContributor(Map<String, CqlSession> sessions) {
			return createContributor(sessions);
		}

	}

	@Configuration(proxyBeanMethods = false)
	@ConditionalOnClass(CassandraOperations.class)
	@ConditionalOnBean(CassandraOperations.class)
	@Deprecated
	static class CassandraOperationsConfiguration extends
			CompositeHealthContributorConfiguration<org.springframework.boot.actuate.cassandra.CassandraHealthIndicator, CassandraOperations> {

		@Bean
		@ConditionalOnMissingBean(name = { "cassandraHealthIndicator", "cassandraHealthContributor" })
		HealthContributor cassandraHealthContributor(Map<String, CassandraOperations> cassandraOperations) {
			return createContributor(cassandraOperations);
		}

	}

	@Configuration(proxyBeanMethods = false)
	@ConditionalOnBean(CqlSession.class)
	static class CassandraReactiveDriverConfiguration extends
			CompositeReactiveHealthContributorConfiguration<CassandraDriverReactiveHealthIndicator, CqlSession> {

		@Bean
		@ConditionalOnMissingBean(name = { "cassandraHealthIndicator", "cassandraHealthContributor" })
		ReactiveHealthContributor cassandraHealthContributor(Map<String, CqlSession> sessions) {
			return createContributor(sessions);
		}

	}

	@Configuration(proxyBeanMethods = false)
	@ConditionalOnClass(ReactiveCassandraOperations.class)
	@ConditionalOnBean(ReactiveCassandraOperations.class)
	@Deprecated
	static class CassandraReactiveOperationsConfiguration extends
			CompositeReactiveHealthContributorConfiguration<org.springframework.boot.actuate.cassandra.CassandraReactiveHealthIndicator, ReactiveCassandraOperations> {

		@Bean
		@ConditionalOnMissingBean(name = { "cassandraHealthIndicator", "cassandraHealthContributor" })
		ReactiveHealthContributor cassandraHealthContributor(
				Map<String, ReactiveCassandraOperations> reactiveCassandraOperations) {
			return createContributor(reactiveCassandraOperations);
		}

	}

}
