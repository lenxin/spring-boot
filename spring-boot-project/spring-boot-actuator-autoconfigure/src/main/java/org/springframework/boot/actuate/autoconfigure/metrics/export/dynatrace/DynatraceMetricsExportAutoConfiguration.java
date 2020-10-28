package org.springframework.boot.actuate.autoconfigure.metrics.export.dynatrace;

import io.micrometer.core.instrument.Clock;
import io.micrometer.core.ipc.http.HttpUrlConnectionSender;
import io.micrometer.dynatrace.DynatraceConfig;
import io.micrometer.dynatrace.DynatraceMeterRegistry;

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
 * {@link EnableAutoConfiguration Auto-configuration} for exporting metrics to Dynatrace.
 *


 * @since 2.1.0
 */
@Configuration(proxyBeanMethods = false)
@AutoConfigureBefore({ CompositeMeterRegistryAutoConfiguration.class, SimpleMetricsExportAutoConfiguration.class })
@AutoConfigureAfter(MetricsAutoConfiguration.class)
@ConditionalOnBean(Clock.class)
@ConditionalOnClass(DynatraceMeterRegistry.class)
@ConditionalOnEnabledMetricsExport("dynatrace")
@EnableConfigurationProperties(DynatraceProperties.class)
public class DynatraceMetricsExportAutoConfiguration {

	private final DynatraceProperties properties;

	public DynatraceMetricsExportAutoConfiguration(DynatraceProperties properties) {
		this.properties = properties;
	}

	@Bean
	@ConditionalOnMissingBean
	public DynatraceConfig dynatraceConfig() {
		return new DynatracePropertiesConfigAdapter(this.properties);
	}

	@Bean
	@ConditionalOnMissingBean
	public DynatraceMeterRegistry dynatraceMeterRegistry(DynatraceConfig dynatraceConfig, Clock clock) {
		return DynatraceMeterRegistry.builder(dynatraceConfig).clock(clock).httpClient(
				new HttpUrlConnectionSender(this.properties.getConnectTimeout(), this.properties.getReadTimeout()))
				.build();
	}

}
