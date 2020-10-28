package org.springframework.boot.actuate.autoconfigure.metrics.export.ganglia;

import io.micrometer.core.instrument.Clock;
import io.micrometer.ganglia.GangliaConfig;
import io.micrometer.ganglia.GangliaMeterRegistry;

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
 * {@link EnableAutoConfiguration Auto-configuration} for exporting metrics to Ganglia.
 *

 * @since 2.0.0
 */
@Configuration(proxyBeanMethods = false)
@AutoConfigureBefore({ CompositeMeterRegistryAutoConfiguration.class, SimpleMetricsExportAutoConfiguration.class })
@AutoConfigureAfter(MetricsAutoConfiguration.class)
@ConditionalOnBean(Clock.class)
@ConditionalOnClass(GangliaMeterRegistry.class)
@ConditionalOnEnabledMetricsExport("ganglia")
@EnableConfigurationProperties(GangliaProperties.class)
public class GangliaMetricsExportAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean
	public GangliaConfig gangliaConfig(GangliaProperties gangliaProperties) {
		return new GangliaPropertiesConfigAdapter(gangliaProperties);
	}

	@Bean
	@ConditionalOnMissingBean
	public GangliaMeterRegistry gangliaMeterRegistry(GangliaConfig gangliaConfig, Clock clock) {
		return new GangliaMeterRegistry(gangliaConfig, clock);
	}

}
