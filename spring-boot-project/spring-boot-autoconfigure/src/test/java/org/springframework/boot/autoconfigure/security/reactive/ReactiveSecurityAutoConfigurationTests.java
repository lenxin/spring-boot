package org.springframework.boot.autoconfigure.security.reactive;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.FilteredClassLoader;
import org.springframework.boot.test.context.runner.ReactiveWebApplicationContextRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.web.server.WebFilterChainProxy;
import org.springframework.web.reactive.config.WebFluxConfigurer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

/**
 * Tests for {@link ReactiveSecurityAutoConfiguration}.
 *

 */
class ReactiveSecurityAutoConfigurationTests {

	private final ReactiveWebApplicationContextRunner contextRunner = new ReactiveWebApplicationContextRunner();

	@Test
	void backsOffWhenWebFilterChainProxyBeanPresent() {
		this.contextRunner.withConfiguration(AutoConfigurations.of(ReactiveSecurityAutoConfiguration.class))
				.withUserConfiguration(WebFilterChainProxyConfiguration.class)
				.run((context) -> assertThat(context).hasSingleBean(WebFilterChainProxy.class));
	}

	@Test
	void enablesWebFluxSecurity() {
		this.contextRunner
				.withConfiguration(AutoConfigurations.of(ReactiveSecurityAutoConfiguration.class,
						ReactiveUserDetailsServiceAutoConfiguration.class))
				.run((context) -> assertThat(context).getBean(WebFilterChainProxy.class).isNotNull());
	}

	@Test
	void autoConfigurationIsConditionalOnClass() {
		this.contextRunner
				.withClassLoader(new FilteredClassLoader(Flux.class, EnableWebFluxSecurity.class,
						WebFilterChainProxy.class, WebFluxConfigurer.class))
				.withConfiguration(AutoConfigurations.of(ReactiveSecurityAutoConfiguration.class,
						ReactiveUserDetailsServiceAutoConfiguration.class))
				.run((context) -> assertThat(context).doesNotHaveBean(WebFilterChainProxy.class));
	}

	@Configuration(proxyBeanMethods = false)
	static class WebFilterChainProxyConfiguration {

		@Bean
		WebFilterChainProxy webFilterChainProxy() {
			return mock(WebFilterChainProxy.class);
		}

	}

}
