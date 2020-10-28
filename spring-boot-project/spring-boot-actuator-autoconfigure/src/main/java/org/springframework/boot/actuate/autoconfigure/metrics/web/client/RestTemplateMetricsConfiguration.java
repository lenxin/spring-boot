package org.springframework.boot.actuate.autoconfigure.metrics.web.client;

import io.micrometer.core.instrument.MeterRegistry;

import org.springframework.boot.actuate.autoconfigure.metrics.MetricsProperties;
import org.springframework.boot.actuate.autoconfigure.metrics.MetricsProperties.Web.Client.ClientRequest;
import org.springframework.boot.actuate.metrics.web.client.DefaultRestTemplateExchangeTagsProvider;
import org.springframework.boot.actuate.metrics.web.client.MetricsRestTemplateCustomizer;
import org.springframework.boot.actuate.metrics.web.client.RestTemplateExchangeTagsProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Configure the instrumentation of {@link RestTemplate}.
 *



 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(RestTemplate.class)
@ConditionalOnBean(RestTemplateBuilder.class)
class RestTemplateMetricsConfiguration {

	@Bean
	@ConditionalOnMissingBean(RestTemplateExchangeTagsProvider.class)
	DefaultRestTemplateExchangeTagsProvider restTemplateExchangeTagsProvider() {
		return new DefaultRestTemplateExchangeTagsProvider();
	}

	@Bean
	MetricsRestTemplateCustomizer metricsRestTemplateCustomizer(MeterRegistry meterRegistry,
			RestTemplateExchangeTagsProvider restTemplateExchangeTagsProvider, MetricsProperties properties) {
		ClientRequest request = properties.getWeb().getClient().getRequest();
		return new MetricsRestTemplateCustomizer(meterRegistry, restTemplateExchangeTagsProvider,
				request.getMetricName(), request.getAutotime());
	}

}
