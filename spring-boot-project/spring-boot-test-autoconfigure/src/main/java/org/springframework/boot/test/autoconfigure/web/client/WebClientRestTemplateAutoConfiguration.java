package org.springframework.boot.test.autoconfigure.web.client;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.web.client.RestTemplateAutoConfiguration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Auto-configuration for a web-client {@link RestTemplate}. Used when
 * {@link AutoConfigureWebClient#registerRestTemplate()} is {@code true}.
 *

 * @since 1.4.0
 * @see AutoConfigureMockRestServiceServer
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnProperty(prefix = "spring.test.webclient", name = "register-rest-template")
@AutoConfigureAfter(RestTemplateAutoConfiguration.class)
public class WebClientRestTemplateAutoConfiguration {

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

}
