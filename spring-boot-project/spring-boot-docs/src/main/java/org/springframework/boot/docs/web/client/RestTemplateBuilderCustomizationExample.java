package org.springframework.boot.docs.web.client;

import java.time.Duration;

import org.springframework.boot.autoconfigure.web.client.RestTemplateBuilderConfigurer;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Example configuration for using a {@link RestTemplateBuilderConfigurer} to configure a
 * custom {@link RestTemplateBuilder}.
 *

 */
@Configuration(proxyBeanMethods = false)
public class RestTemplateBuilderCustomizationExample {

	// tag::customizer[]
	@Bean
	public RestTemplateBuilder restTemplateBuilder(RestTemplateBuilderConfigurer configurer) {
		return configurer.configure(new RestTemplateBuilder()).setConnectTimeout(Duration.ofSeconds(5))
				.setReadTimeout(Duration.ofSeconds(2));
	}
	// end::customizer[]

}
