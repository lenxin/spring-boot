package org.springframework.boot.actuate.autoconfigure.metrics.export.statsd;

import io.micrometer.core.instrument.Clock;
import io.micrometer.statsd.StatsdConfig;
import io.micrometer.statsd.StatsdMeterRegistry;

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
 * {@link EnableAutoConfiguration Auto-configuration} for exporting metrics to StatsD.
 *

 * @since 2.0.0
 */
@Configuration(proxyBeanMethods = false)
@AutoConfigureBefore({ CompositeMeterRegistryAutoConfiguration.class, SimpleMetricsExportAutoConfiguration.class })
@AutoConfigureAfter(MetricsAutoConfiguration.class)
@ConditionalOnBean(Clock.class)
@ConditionalOnClass(StatsdMeterRegistry.class)
@ConditionalOnEnabledMetricsExport("statsd")
@EnableConfigurationProperties(StatsdProperties.class)
public class StatsdMetricsExportAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean
	public StatsdConfig statsdConfig(StatsdProperties statsdProperties) {
		return new StatsdPropertiesConfigAdapter(statsdProperties);
	}

	@Bean
	@ConditionalOnMissingBean
	public StatsdMeterRegistry statsdMeterRegistry(StatsdConfig statsdConfig, Clock clock) {
		return new StatsdMeterRegistry(statsdConfig, clock);
	}

}
