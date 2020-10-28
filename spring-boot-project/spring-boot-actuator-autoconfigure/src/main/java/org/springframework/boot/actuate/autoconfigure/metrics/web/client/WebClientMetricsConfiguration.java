package org.springframework.boot.actuate.autoconfigure.metrics.web.client;

import io.micrometer.core.instrument.MeterRegistry;

import org.springframework.boot.actuate.autoconfigure.metrics.MetricsProperties;
import org.springframework.boot.actuate.autoconfigure.metrics.MetricsProperties.Web.Client.ClientRequest;
import org.springframework.boot.actuate.metrics.web.reactive.client.DefaultWebClientExchangeTagsProvider;
import org.springframework.boot.actuate.metrics.web.reactive.client.MetricsWebClientCustomizer;
import org.springframework.boot.actuate.metrics.web.reactive.client.WebClientExchangeTagsProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Configure the instrumentation of {@link WebClient}.
 *


 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(WebClient.class)
class WebClientMetricsConfiguration {

	@Bean
	@ConditionalOnMissingBean
	WebClientExchangeTagsProvider defaultWebClientExchangeTagsProvider() {
		return new DefaultWebClientExchangeTagsProvider();
	}

	@Bean
	MetricsWebClientCustomizer metricsWebClientCustomizer(MeterRegistry meterRegistry,
			WebClientExchangeTagsProvider tagsProvider, MetricsProperties properties) {
		ClientRequest request = properties.getWeb().getClient().getRequest();
		return new MetricsWebClientCustomizer(meterRegistry, tagsProvider, request.getMetricName(),
				request.getAutotime());
	}

}
