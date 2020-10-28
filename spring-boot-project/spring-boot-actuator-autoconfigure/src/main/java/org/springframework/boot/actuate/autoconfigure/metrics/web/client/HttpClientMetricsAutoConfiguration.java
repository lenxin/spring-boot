package org.springframework.boot.actuate.autoconfigure.metrics.web.client;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.config.MeterFilter;

import org.springframework.boot.actuate.autoconfigure.metrics.CompositeMeterRegistryAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.metrics.MetricsAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.metrics.MetricsProperties;
import org.springframework.boot.actuate.autoconfigure.metrics.OnlyOnceLoggingDenyMeterFilter;
import org.springframework.boot.actuate.autoconfigure.metrics.export.simple.SimpleMetricsExportAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.web.client.RestTemplateAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for HTTP client-related metrics.
 *




 * @since 2.1.0
 */
@Configuration(proxyBeanMethods = false)
@AutoConfigureAfter({ MetricsAutoConfiguration.class, CompositeMeterRegistryAutoConfiguration.class,
		SimpleMetricsExportAutoConfiguration.class, RestTemplateAutoConfiguration.class })
@ConditionalOnClass(MeterRegistry.class)
@ConditionalOnBean(MeterRegistry.class)
@Import({ RestTemplateMetricsConfiguration.class, WebClientMetricsConfiguration.class })
public class HttpClientMetricsAutoConfiguration {

	@Bean
	@Order(0)
	public MeterFilter metricsHttpClientUriTagFilter(MetricsProperties properties) {
		String metricName = properties.getWeb().getClient().getRequest().getMetricName();
		MeterFilter denyFilter = new OnlyOnceLoggingDenyMeterFilter(() -> String
				.format("Reached the maximum number of URI tags for '%s'. Are you using 'uriVariables'?", metricName));
		return MeterFilter.maximumAllowableTags(metricName, "uri", properties.getWeb().getClient().getMaxUriTags(),
				denyFilter);
	}

}
