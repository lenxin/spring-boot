package org.springframework.boot.actuate.autoconfigure.metrics.web.reactive;

import java.util.stream.Collectors;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.config.MeterFilter;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.actuate.autoconfigure.metrics.CompositeMeterRegistryAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.metrics.MetricsAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.metrics.MetricsProperties;
import org.springframework.boot.actuate.autoconfigure.metrics.MetricsProperties.Web.Server.ServerRequest;
import org.springframework.boot.actuate.autoconfigure.metrics.OnlyOnceLoggingDenyMeterFilter;
import org.springframework.boot.actuate.autoconfigure.metrics.export.simple.SimpleMetricsExportAutoConfiguration;
import org.springframework.boot.actuate.metrics.web.reactive.server.DefaultWebFluxTagsProvider;
import org.springframework.boot.actuate.metrics.web.reactive.server.MetricsWebFilter;
import org.springframework.boot.actuate.metrics.web.reactive.server.WebFluxTagsContributor;
import org.springframework.boot.actuate.metrics.web.reactive.server.WebFluxTagsProvider;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for instrumentation of Spring
 * WebFlux applications.
 *


 * @since 2.0.0
 */
@Configuration(proxyBeanMethods = false)
@AutoConfigureAfter({ MetricsAutoConfiguration.class, CompositeMeterRegistryAutoConfiguration.class,
		SimpleMetricsExportAutoConfiguration.class })
@ConditionalOnBean(MeterRegistry.class)
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
public class WebFluxMetricsAutoConfiguration {

	private final MetricsProperties properties;

	public WebFluxMetricsAutoConfiguration(MetricsProperties properties) {
		this.properties = properties;
	}

	@Bean
	@ConditionalOnMissingBean(WebFluxTagsProvider.class)
	public DefaultWebFluxTagsProvider webFluxTagsProvider(ObjectProvider<WebFluxTagsContributor> contributors) {
		return new DefaultWebFluxTagsProvider(this.properties.getWeb().getServer().getRequest().isIgnoreTrailingSlash(),
				contributors.orderedStream().collect(Collectors.toList()));
	}

	@Bean
	public MetricsWebFilter webfluxMetrics(MeterRegistry registry, WebFluxTagsProvider tagConfigurer) {
		ServerRequest request = this.properties.getWeb().getServer().getRequest();
		return new MetricsWebFilter(registry, tagConfigurer, request.getMetricName(), request.getAutotime());
	}

	@Bean
	@Order(0)
	public MeterFilter metricsHttpServerUriTagFilter() {
		String metricName = this.properties.getWeb().getServer().getRequest().getMetricName();
		MeterFilter filter = new OnlyOnceLoggingDenyMeterFilter(
				() -> String.format("Reached the maximum number of URI tags for '%s'.", metricName));
		return MeterFilter.maximumAllowableTags(metricName, "uri", this.properties.getWeb().getServer().getMaxUriTags(),
				filter);
	}

}
