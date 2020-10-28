package org.springframework.boot.actuate.autoconfigure.metrics.export.simple;

import io.micrometer.core.instrument.Clock;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.simple.SimpleConfig;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;

import org.springframework.boot.actuate.autoconfigure.metrics.CompositeMeterRegistryAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.metrics.MetricsAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.metrics.export.ConditionalOnEnabledMetricsExport;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for exporting metrics to a
 * {@link SimpleMeterRegistry}. Auto-configured after all other {@link MeterRegistry}
 * beans and only used as a fallback.
 *

 * @since 2.0.0
 */
@Configuration(proxyBeanMethods = false)
@AutoConfigureAfter(MetricsAutoConfiguration.class)
@AutoConfigureBefore(CompositeMeterRegistryAutoConfiguration.class)
@ConditionalOnBean(Clock.class)
@EnableConfigurationProperties(SimpleProperties.class)
@ConditionalOnMissingBean(MeterRegistry.class)
@ConditionalOnEnabledMetricsExport("simple")
public class SimpleMetricsExportAutoConfiguration {

	@Bean
	public SimpleMeterRegistry simpleMeterRegistry(SimpleConfig config, Clock clock) {
		return new SimpleMeterRegistry(config, clock);
	}

	@Bean
	@ConditionalOnMissingBean
	public SimpleConfig simpleConfig(SimpleProperties simpleProperties) {
		return new SimplePropertiesConfigAdapter(simpleProperties);
	}

}
