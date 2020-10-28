package org.springframework.boot.actuate.autoconfigure.metrics.r2dbc;

import java.util.Map;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;
import io.r2dbc.pool.ConnectionPool;
import io.r2dbc.spi.ConnectionFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.metrics.MetricsAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.metrics.export.simple.SimpleMetricsExportAutoConfiguration;
import org.springframework.boot.actuate.metrics.r2dbc.ConnectionPoolMetrics;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.r2dbc.R2dbcAutoConfiguration;
import org.springframework.context.annotation.Configuration;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for metrics on all available
 * {@link ConnectionFactory R2DBC connection factories}.
 *


 * @since 2.3.0
 */
@Configuration(proxyBeanMethods = false)
@AutoConfigureAfter({ MetricsAutoConfiguration.class, SimpleMetricsExportAutoConfiguration.class,
		R2dbcAutoConfiguration.class })
@ConditionalOnClass({ ConnectionPool.class, MeterRegistry.class })
@ConditionalOnBean({ ConnectionFactory.class, MeterRegistry.class })
public class ConnectionPoolMetricsAutoConfiguration {

	@Autowired
	public void bindConnectionPoolsToRegistry(Map<String, ConnectionFactory> connectionFactories,
			MeterRegistry registry) {
		connectionFactories.forEach((beanName, connectionFactory) -> {
			if (connectionFactory instanceof ConnectionPool) {
				new ConnectionPoolMetrics((ConnectionPool) connectionFactory, beanName, Tags.empty()).bindTo(registry);
			}
		});
	}

}
