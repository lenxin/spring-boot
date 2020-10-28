package org.springframework.boot.actuate.autoconfigure.metrics.export.signalfx;

import io.micrometer.core.instrument.Clock;
import io.micrometer.signalfx.SignalFxConfig;
import io.micrometer.signalfx.SignalFxMeterRegistry;

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
 * {@link EnableAutoConfiguration Auto-configuration} for exporting metrics to SignalFX.
 *


 * @since 2.0.0
 */
@Configuration(proxyBeanMethods = false)
@AutoConfigureBefore({ CompositeMeterRegistryAutoConfiguration.class, SimpleMetricsExportAutoConfiguration.class })
@AutoConfigureAfter(MetricsAutoConfiguration.class)
@ConditionalOnBean(Clock.class)
@ConditionalOnClass(SignalFxMeterRegistry.class)
@ConditionalOnEnabledMetricsExport("signalfx")
@EnableConfigurationProperties(SignalFxProperties.class)
public class SignalFxMetricsExportAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean
	public SignalFxConfig signalfxConfig(SignalFxProperties props) {
		return new SignalFxPropertiesConfigAdapter(props);
	}

	@Bean
	@ConditionalOnMissingBean
	public SignalFxMeterRegistry signalFxMeterRegistry(SignalFxConfig config, Clock clock) {
		return new SignalFxMeterRegistry(config, clock);
	}

}
