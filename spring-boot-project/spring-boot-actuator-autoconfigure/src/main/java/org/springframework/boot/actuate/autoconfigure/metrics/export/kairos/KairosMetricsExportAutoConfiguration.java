package org.springframework.boot.actuate.autoconfigure.metrics.export.kairos;

import io.micrometer.core.instrument.Clock;
import io.micrometer.core.ipc.http.HttpUrlConnectionSender;
import io.micrometer.kairos.KairosConfig;
import io.micrometer.kairos.KairosMeterRegistry;

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
 * {@link EnableAutoConfiguration Auto-configuration} for exporting metrics to KairosDB.
 *


 * @since 2.1.0
 */
@Configuration(proxyBeanMethods = false)
@AutoConfigureBefore({ CompositeMeterRegistryAutoConfiguration.class, SimpleMetricsExportAutoConfiguration.class })
@AutoConfigureAfter(MetricsAutoConfiguration.class)
@ConditionalOnBean(Clock.class)
@ConditionalOnClass(KairosMeterRegistry.class)
@ConditionalOnEnabledMetricsExport("kairos")
@EnableConfigurationProperties(KairosProperties.class)
public class KairosMetricsExportAutoConfiguration {

	private final KairosProperties properties;

	public KairosMetricsExportAutoConfiguration(KairosProperties properties) {
		this.properties = properties;
	}

	@Bean
	@ConditionalOnMissingBean
	public KairosConfig kairosConfig() {
		return new KairosPropertiesConfigAdapter(this.properties);
	}

	@Bean
	@ConditionalOnMissingBean
	public KairosMeterRegistry kairosMeterRegistry(KairosConfig kairosConfig, Clock clock) {
		return KairosMeterRegistry.builder(kairosConfig).clock(clock).httpClient(
				new HttpUrlConnectionSender(this.properties.getConnectTimeout(), this.properties.getReadTimeout()))
				.build();

	}

}
