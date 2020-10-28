package org.springframework.boot.actuate.autoconfigure.metrics.export.appoptics;

import io.micrometer.appoptics.AppOpticsConfig;
import io.micrometer.appoptics.AppOpticsMeterRegistry;
import io.micrometer.core.instrument.Clock;
import io.micrometer.core.ipc.http.HttpUrlConnectionSender;

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
 * {@link EnableAutoConfiguration Auto-configuration} for exporting metrics to AppOptics.
 *


 * @since 2.1.0
 */
@Configuration(proxyBeanMethods = false)
@AutoConfigureBefore({ CompositeMeterRegistryAutoConfiguration.class, SimpleMetricsExportAutoConfiguration.class })
@AutoConfigureAfter(MetricsAutoConfiguration.class)
@ConditionalOnBean(Clock.class)
@ConditionalOnClass(AppOpticsMeterRegistry.class)
@ConditionalOnEnabledMetricsExport("appoptics")
@EnableConfigurationProperties(AppOpticsProperties.class)
public class AppOpticsMetricsExportAutoConfiguration {

	private final AppOpticsProperties properties;

	public AppOpticsMetricsExportAutoConfiguration(AppOpticsProperties properties) {
		this.properties = properties;
	}

	@Bean
	@ConditionalOnMissingBean
	public AppOpticsConfig appOpticsConfig() {
		return new AppOpticsPropertiesConfigAdapter(this.properties);
	}

	@Bean
	@ConditionalOnMissingBean
	public AppOpticsMeterRegistry appOpticsMeterRegistry(AppOpticsConfig config, Clock clock) {
		return AppOpticsMeterRegistry.builder(config).clock(clock).httpClient(
				new HttpUrlConnectionSender(this.properties.getConnectTimeout(), this.properties.getReadTimeout()))
				.build();
	}

}
