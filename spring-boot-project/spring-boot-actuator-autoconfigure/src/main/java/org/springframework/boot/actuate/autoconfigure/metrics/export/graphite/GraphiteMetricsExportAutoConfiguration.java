package org.springframework.boot.actuate.autoconfigure.metrics.export.graphite;

import io.micrometer.core.instrument.Clock;
import io.micrometer.graphite.GraphiteConfig;
import io.micrometer.graphite.GraphiteMeterRegistry;

import org.springframework.boot.actuate.autoconfigure.metrics.CompositeMeterRegistryAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.metrics.MetricsAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.metrics.export.ConditionalOnEnabledMetricsExport;
import org.springframework.boot.actuate.autoconfigure.metrics.export.simple.SimpleMetricsExportAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for exporting metrics to Graphite.
 *

 * @since 2.0.0
 */
@Configuration(proxyBeanMethods = false)
@AutoConfigureBefore({ CompositeMeterRegistryAutoConfiguration.class, SimpleMetricsExportAutoConfiguration.class })
@AutoConfigureAfter(MetricsAutoConfiguration.class)
@ConditionalOnBean(Clock.class)
@ConditionalOnClass(GraphiteMeterRegistry.class)
@ConditionalOnEnabledMetricsExport("graphite")
@EnableConfigurationProperties(GraphiteProperties.class)
public class GraphiteMetricsExportAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean
	public GraphiteConfig graphiteConfig(GraphiteProperties graphiteProperties) {
		return new GraphitePropertiesConfigAdapter(graphiteProperties);
	}

	@Bean
	@ConditionalOnMissingBean
	public GraphiteMeterRegistry graphiteMeterRegistry(GraphiteConfig graphiteConfig, Clock clock) {
		return new GraphiteMeterRegistry(graphiteConfig, clock);
	}

}
