package org.springframework.boot.actuate.autoconfigure.metrics.export.influx;

import io.micrometer.core.instrument.Clock;
import io.micrometer.core.ipc.http.HttpUrlConnectionSender;
import io.micrometer.influx.InfluxConfig;
import io.micrometer.influx.InfluxMeterRegistry;

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
 * {@link EnableAutoConfiguration Auto-configuration} for exporting metrics to Influx.
 *


 * @since 2.0.0
 */
@Configuration(proxyBeanMethods = false)
@AutoConfigureBefore({ CompositeMeterRegistryAutoConfiguration.class, SimpleMetricsExportAutoConfiguration.class })
@AutoConfigureAfter(MetricsAutoConfiguration.class)
@ConditionalOnBean(Clock.class)
@ConditionalOnClass(InfluxMeterRegistry.class)
@ConditionalOnEnabledMetricsExport("influx")
@EnableConfigurationProperties(InfluxProperties.class)
public class InfluxMetricsExportAutoConfiguration {

	private final InfluxProperties properties;

	public InfluxMetricsExportAutoConfiguration(InfluxProperties properties) {
		this.properties = properties;
	}

	@Bean
	@ConditionalOnMissingBean
	public InfluxConfig influxConfig() {
		return new InfluxPropertiesConfigAdapter(this.properties);
	}

	@Bean
	@ConditionalOnMissingBean
	public InfluxMeterRegistry influxMeterRegistry(InfluxConfig influxConfig, Clock clock,
			InfluxProperties influxProperties) {
		return InfluxMeterRegistry.builder(influxConfig).clock(clock).httpClient(
				new HttpUrlConnectionSender(this.properties.getConnectTimeout(), this.properties.getReadTimeout()))
				.build();

	}

}
