package org.springframework.boot.actuate.autoconfigure.trace.http;

import org.springframework.boot.actuate.autoconfigure.endpoint.condition.ConditionalOnAvailableEndpoint;
import org.springframework.boot.actuate.trace.http.HttpTraceEndpoint;
import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for the {@link HttpTraceEndpoint}.
 *

 * @since 2.0.0
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnAvailableEndpoint(endpoint = HttpTraceEndpoint.class)
@AutoConfigureAfter(HttpTraceAutoConfiguration.class)
public class HttpTraceEndpointAutoConfiguration {

	@Bean
	@ConditionalOnBean(HttpTraceRepository.class)
	@ConditionalOnMissingBean
	public HttpTraceEndpoint httpTraceEndpoint(HttpTraceRepository traceRepository) {
		return new HttpTraceEndpoint(traceRepository);
	}

}
