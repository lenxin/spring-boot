package org.springframework.boot.actuate.autoconfigure.cassandra;

import com.datastax.oss.driver.api.core.CqlSession;

import org.springframework.boot.actuate.autoconfigure.cassandra.CassandraHealthContributorConfigurations.CassandraDriverConfiguration;
import org.springframework.boot.actuate.autoconfigure.health.ConditionalOnEnabledHealthIndicator;
import org.springframework.boot.actuate.cassandra.CassandraDriverHealthIndicator;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.cassandra.CassandraAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.data.cassandra.CassandraDataAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for
 * {@link CassandraDriverHealthIndicator}.
 *


 * @since 2.1.0
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(CqlSession.class)
@ConditionalOnEnabledHealthIndicator("cassandra")
@AutoConfigureAfter({ CassandraAutoConfiguration.class, CassandraDataAutoConfiguration.class,
		CassandraReactiveHealthContributorAutoConfiguration.class })
@Import({ CassandraDriverConfiguration.class,
		CassandraHealthContributorConfigurations.CassandraOperationsConfiguration.class })
@SuppressWarnings("deprecation")
public class CassandraHealthContributorAutoConfiguration {

}
