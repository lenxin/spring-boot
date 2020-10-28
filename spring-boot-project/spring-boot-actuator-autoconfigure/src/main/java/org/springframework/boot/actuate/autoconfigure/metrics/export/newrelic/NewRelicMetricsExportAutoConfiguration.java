package org.springframework.boot.actuate.autoconfigure.metrics.export.newrelic;

import io.micrometer.core.instrument.Clock;
import io.micrometer.core.ipc.http.HttpUrlConnectionSender;
import io.micrometer.newrelic.ClientProviderType;
import io.micrometer.newrelic.NewRelicClientProvider;
import io.micrometer.newrelic.NewRelicConfig;
import io.micrometer.newrelic.NewRelicInsightsAgentClientProvider;
import io.micrometer.newrelic.NewRelicInsightsApiClientProvider;
import io.micrometer.newrelic.NewRelicMeterRegistry;

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
 * {@link EnableAutoConfiguration Auto-configuration} for exporting metrics to New Relic.
 *



 * @since 2.0.0
 */
@Configuration(proxyBeanMethods = false)
@AutoConfigureBefore({ CompositeMeterRegistryAutoConfiguration.class, SimpleMetricsExportAutoConfiguration.class })
@AutoConfigureAfter(MetricsAutoConfiguration.class)
@ConditionalOnBean(Clock.class)
@ConditionalOnClass(NewRelicMeterRegistry.class)
@ConditionalOnEnabledMetricsExport("newrelic")
@EnableConfigurationProperties(NewRelicProperties.class)
public class NewRelicMetricsExportAutoConfiguration {

	private final NewRelicProperties properties;

	public NewRelicMetricsExportAutoConfiguration(NewRelicProperties properties) {
		this.properties = properties;
	}

	@Bean
	@ConditionalOnMissingBean
	public NewRelicConfig newRelicConfig() {
		return new NewRelicPropertiesConfigAdapter(this.properties);
	}

	@Bean
	@ConditionalOnMissingBean
	public NewRelicClientProvider newRelicClientProvider(NewRelicConfig newRelicConfig) {
		if (newRelicConfig.clientProviderType() == ClientProviderType.INSIGHTS_AGENT) {
			return new NewRelicInsightsAgentClientProvider(newRelicConfig);
		}
		return new NewRelicInsightsApiClientProvider(newRelicConfig,
				new HttpUrlConnectionSender(this.properties.getConnectTimeout(), this.properties.getReadTimeout()));

	}

	@Bean
	@ConditionalOnMissingBean
	public NewRelicMeterRegistry newRelicMeterRegistry(NewRelicConfig newRelicConfig, Clock clock,
			NewRelicClientProvider newRelicClientProvider) {
		return NewRelicMeterRegistry.builder(newRelicConfig).clock(clock).clientProvider(newRelicClientProvider)
				.build();
	}

}
