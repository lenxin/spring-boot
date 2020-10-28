package org.springframework.boot.actuate.autoconfigure.web.servlet;

import org.junit.jupiter.api.Test;

import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletPath;
import org.springframework.boot.test.context.runner.WebApplicationContextRunner;
import org.springframework.boot.web.servlet.filter.OrderedRequestContextFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.filter.RequestContextFilter;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link WebMvcEndpointChildContextConfiguration}.
 *

 */
class WebMvcEndpointChildContextConfigurationTests {

	private final WebApplicationContextRunner contextRunner = new WebApplicationContextRunner()
			.withAllowBeanDefinitionOverriding(true);

	@Test
	void contextShouldConfigureRequestContextFilter() {
		this.contextRunner.withUserConfiguration(WebMvcEndpointChildContextConfiguration.class)
				.run((context) -> assertThat(context).hasSingleBean(OrderedRequestContextFilter.class));
	}

	@Test
	void contextShouldNotConfigureRequestContextFilterWhenPresent() {
		this.contextRunner.withUserConfiguration(ExistingConfig.class, WebMvcEndpointChildContextConfiguration.class)
				.run((context) -> {
					assertThat(context).hasSingleBean(RequestContextFilter.class);
					assertThat(context).hasBean("testRequestContextFilter");
				});
	}

	@Test
	void contextShouldNotConfigureRequestContextFilterWhenRequestContextListenerPresent() {
		this.contextRunner.withUserConfiguration(RequestContextListenerConfig.class,
				WebMvcEndpointChildContextConfiguration.class).run((context) -> {
					assertThat(context).hasSingleBean(RequestContextListener.class);
					assertThat(context).doesNotHaveBean(OrderedRequestContextFilter.class);
				});
	}

	@Test
	void contextShouldConfigureDispatcherServletPathWithRootPath() {
		this.contextRunner.withUserConfiguration(WebMvcEndpointChildContextConfiguration.class)
				.run((context) -> assertThat(context.getBean(DispatcherServletPath.class).getPath()).isEqualTo("/"));
	}

	@Configuration(proxyBeanMethods = false)
	static class ExistingConfig {

		@Bean
		RequestContextFilter testRequestContextFilter() {
			return new RequestContextFilter();
		}

	}

	@Configuration(proxyBeanMethods = false)
	static class RequestContextListenerConfig {

		@Bean
		RequestContextListener testRequestContextListener() {
			return new RequestContextListener();
		}

	}

}
