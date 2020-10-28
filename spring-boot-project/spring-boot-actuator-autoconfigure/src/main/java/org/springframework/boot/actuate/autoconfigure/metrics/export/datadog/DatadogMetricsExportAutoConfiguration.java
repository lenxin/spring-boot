package org.springframework.boot.actuate.autoconfigure.metrics.export.datadog;

import io.micrometer.core.instrument.Clock;
import io.micrometer.core.ipc.http.HttpUrlConnectionSender;
import io.micrometer.datadog.DatadogConfig;
import io.micrometer.datadog.DatadogMeterRegistry;

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
 * {@link EnableAutoConfiguration Auto-configuration} for exporting metrics to Datadog.
 *


 * @since 2.0.0
 */
@Configuration(proxyBeanMethods = false)
@AutoConfigureBefore({ CompositeMeterRegistryAutoConfiguration.class, SimpleMetricsExportAutoConfiguration.class })
@AutoConfigureAfter(MetricsAutoConfiguration.class)
@ConditionalOnBean(Clock.class)
@ConditionalOnClass(DatadogMeterRegistry.class)
@ConditionalOnEnabledMetricsExport("datadog")
@EnableConfigurationProperties(DatadogProperties.class)
public class DatadogMetricsExportAutoConfiguration {

	private final DatadogProperties properties;

	public DatadogMetricsExportAutoConfiguration(DatadogProperties properties) {
		this.properties = properties;
	}

	@Bean
	@ConditionalOnMissingBean
	public DatadogConfig datadogConfig() {
		return new DatadogPropertiesConfigAdapter(this.properties);
	}

	@Bean
	@ConditionalOnMissingBean
	public DatadogMeterRegistry datadogMeterRegistry(DatadogConfig datadogConfig, Clock clock) {
		return DatadogMeterRegistry.builder(datadogConfig).clock(clock).httpClient(
				new HttpUrlConnectionSender(this.properties.getConnectTimeout(), this.properties.getReadTimeout()))
				.build();
	}

}
