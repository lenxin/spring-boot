package org.springframework.boot.actuate.autoconfigure.metrics.web.jetty;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.jetty.JettyServerThreadPoolMetrics;
import org.eclipse.jetty.server.Server;

import org.springframework.boot.actuate.autoconfigure.metrics.CompositeMeterRegistryAutoConfiguration;
import org.springframework.boot.actuate.metrics.web.jetty.JettyServerThreadPoolMetricsBinder;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for Jetty metrics.
 *

 * @since 2.1.0
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnWebApplication
@ConditionalOnClass({ JettyServerThreadPoolMetrics.class, Server.class })
@AutoConfigureAfter(CompositeMeterRegistryAutoConfiguration.class)
public class JettyMetricsAutoConfiguration {

	@Bean
	@ConditionalOnBean(MeterRegistry.class)
	@ConditionalOnMissingBean({ JettyServerThreadPoolMetrics.class, JettyServerThreadPoolMetricsBinder.class })
	public JettyServerThreadPoolMetricsBinder jettyServerThreadPoolMetricsBinder(MeterRegistry meterRegistry) {
		return new JettyServerThreadPoolMetricsBinder(meterRegistry);
	}

}
