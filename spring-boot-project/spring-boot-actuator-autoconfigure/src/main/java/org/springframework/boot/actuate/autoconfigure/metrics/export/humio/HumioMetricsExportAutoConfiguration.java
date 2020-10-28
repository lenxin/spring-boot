package org.springframework.boot.actuate.autoconfigure.metrics.export.humio;

import io.micrometer.core.instrument.Clock;
import io.micrometer.core.ipc.http.HttpUrlConnectionSender;
import io.micrometer.humio.HumioConfig;
import io.micrometer.humio.HumioMeterRegistry;

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
 * {@link EnableAutoConfiguration Auto-configuration} for exporting metrics to Humio.
 *


 * @since 2.1.0
 */
@Configuration(proxyBeanMethods = false)
@AutoConfigureBefore({ CompositeMeterRegistryAutoConfiguration.class, SimpleMetricsExportAutoConfiguration.class })
@AutoConfigureAfter(MetricsAutoConfiguration.class)
@ConditionalOnBean(Clock.class)
@ConditionalOnClass(HumioMeterRegistry.class)
@ConditionalOnEnabledMetricsExport("humio")
@EnableConfigurationProperties(HumioProperties.class)
public class HumioMetricsExportAutoConfiguration {

	private final HumioProperties properties;

	public HumioMetricsExportAutoConfiguration(HumioProperties properties) {
		this.properties = properties;
	}

	@Bean
	@ConditionalOnMissingBean
	public HumioConfig humioConfig() {
		return new HumioPropertiesConfigAdapter(this.properties);
	}

	@Bean
	@ConditionalOnMissingBean
	public HumioMeterRegistry humioMeterRegistry(HumioConfig humioConfig, Clock clock) {
		return HumioMeterRegistry.builder(humioConfig).clock(clock).httpClient(
				new HttpUrlConnectionSender(this.properties.getConnectTimeout(), this.properties.getReadTimeout()))
				.build();

	}

}
