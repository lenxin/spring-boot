package org.springframework.boot.actuate.autoconfigure.web.trace;

import org.junit.jupiter.api.Test;

import org.springframework.boot.actuate.autoconfigure.trace.http.HttpTraceAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.trace.http.HttpTraceEndpointAutoConfiguration;
import org.springframework.boot.actuate.trace.http.HttpTraceEndpoint;
import org.springframework.boot.actuate.trace.http.InMemoryHttpTraceRepository;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.WebApplicationContextRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link HttpTraceEndpointAutoConfiguration}.
 *


 */
class HttpTraceEndpointAutoConfigurationTests {

	private final WebApplicationContextRunner contextRunner = new WebApplicationContextRunner().withConfiguration(
			AutoConfigurations.of(HttpTraceAutoConfiguration.class, HttpTraceEndpointAutoConfiguration.class));

	@Test
	void runWhenRepositoryBeanAvailableShouldHaveEndpointBean() {
		this.contextRunner.withUserConfiguration(HttpTraceRepositoryConfiguration.class)
				.withPropertyValues("management.endpoints.web.exposure.include=httptrace")
				.run((context) -> assertThat(context).hasSingleBean(HttpTraceEndpoint.class));
	}

	@Test
	void runWhenNotExposedShouldNotHaveEndpointBean() {
		this.contextRunner.withUserConfiguration(HttpTraceRepositoryConfiguration.class)
				.run((context) -> assertThat(context).doesNotHaveBean(HttpTraceEndpoint.class));
	}

	@Test
	void runWhenEnabledPropertyIsFalseShouldNotHaveEndpointBean() {
		this.contextRunner.withUserConfiguration(HttpTraceRepositoryConfiguration.class)
				.withPropertyValues("management.endpoints.web.exposure.include=httptrace")
				.withPropertyValues("management.endpoint.httptrace.enabled:false")
				.run((context) -> assertThat(context).doesNotHaveBean(HttpTraceEndpoint.class));
	}

	@Test
	void endpointBacksOffWhenRepositoryIsNotAvailable() {
		this.contextRunner.withPropertyValues("management.endpoints.web.exposure.include=httptrace")
				.run((context) -> assertThat(context).doesNotHaveBean(HttpTraceEndpoint.class));
	}

	@Configuration(proxyBeanMethods = false)
	static class HttpTraceRepositoryConfiguration {

		@Bean
		InMemoryHttpTraceRepository customRepository() {
			return new InMemoryHttpTraceRepository();
		}

	}

}
