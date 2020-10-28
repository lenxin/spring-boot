package org.springframework.boot.actuate.autoconfigure.trace.http;

import org.springframework.boot.actuate.trace.http.HttpExchangeTracer;
import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.boot.actuate.web.trace.reactive.HttpTraceWebFilter;
import org.springframework.boot.actuate.web.trace.servlet.HttpTraceFilter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for HTTP tracing.
 *

 * @since 2.0.0
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnWebApplication
@ConditionalOnProperty(prefix = "management.trace.http", name = "enabled", matchIfMissing = true)
@ConditionalOnBean(HttpTraceRepository.class)
@EnableConfigurationProperties(HttpTraceProperties.class)
public class HttpTraceAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean
	public HttpExchangeTracer httpExchangeTracer(HttpTraceProperties traceProperties) {
		return new HttpExchangeTracer(traceProperties.getInclude());
	}

	@Configuration(proxyBeanMethods = false)
	@ConditionalOnWebApplication(type = Type.SERVLET)
	static class ServletTraceFilterConfiguration {

		@Bean
		@ConditionalOnMissingBean
		HttpTraceFilter httpTraceFilter(HttpTraceRepository repository, HttpExchangeTracer tracer) {
			return new HttpTraceFilter(repository, tracer);
		}

	}

	@Configuration(proxyBeanMethods = false)
	@ConditionalOnWebApplication(type = Type.REACTIVE)
	static class ReactiveTraceFilterConfiguration {

		@Bean
		@ConditionalOnMissingBean
		HttpTraceWebFilter httpTraceWebFilter(HttpTraceRepository repository, HttpExchangeTracer tracer,
				HttpTraceProperties traceProperties) {
			return new HttpTraceWebFilter(repository, tracer, traceProperties.getInclude());
		}

	}

}
