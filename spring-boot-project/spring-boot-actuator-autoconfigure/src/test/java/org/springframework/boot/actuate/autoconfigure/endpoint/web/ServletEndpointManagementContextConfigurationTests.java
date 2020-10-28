package org.springframework.boot.actuate.autoconfigure.endpoint.web;

import java.util.Collections;

import org.glassfish.jersey.server.ResourceConfig;
import org.junit.jupiter.api.Test;

import org.springframework.boot.actuate.endpoint.web.ServletEndpointRegistrar;
import org.springframework.boot.actuate.endpoint.web.annotation.ServletEndpointsSupplier;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletPath;
import org.springframework.boot.autoconfigure.web.servlet.JerseyApplicationPath;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.FilteredClassLoader;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.boot.test.context.runner.WebApplicationContextRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.DispatcherServlet;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link ServletEndpointManagementContextConfiguration}.
 *


 */
class ServletEndpointManagementContextConfigurationTests {

	private final WebApplicationContextRunner contextRunner = new WebApplicationContextRunner()
			.withUserConfiguration(TestConfig.class);

	@Test
	void contextShouldContainServletEndpointRegistrar() {
		FilteredClassLoader classLoader = new FilteredClassLoader(ResourceConfig.class);
		this.contextRunner.withClassLoader(classLoader).run((context) -> {
			assertThat(context).hasSingleBean(ServletEndpointRegistrar.class);
			ServletEndpointRegistrar bean = context.getBean(ServletEndpointRegistrar.class);
			assertThat(bean).hasFieldOrPropertyWithValue("basePath", "/test/actuator");
		});
	}

	@Test
	void contextWhenJerseyShouldContainServletEndpointRegistrar() {
		FilteredClassLoader classLoader = new FilteredClassLoader(DispatcherServlet.class);
		this.contextRunner.withClassLoader(classLoader).run((context) -> {
			assertThat(context).hasSingleBean(ServletEndpointRegistrar.class);
			ServletEndpointRegistrar bean = context.getBean(ServletEndpointRegistrar.class);
			assertThat(bean).hasFieldOrPropertyWithValue("basePath", "/jersey/actuator");
		});
	}

	@Test
	void contextWhenNoServletBasedShouldNotContainServletEndpointRegistrar() {
		new ApplicationContextRunner().withUserConfiguration(TestConfig.class)
				.run((context) -> assertThat(context).doesNotHaveBean(ServletEndpointRegistrar.class));
	}

	@Configuration(proxyBeanMethods = false)
	@Import(ServletEndpointManagementContextConfiguration.class)
	@EnableConfigurationProperties(WebEndpointProperties.class)
	static class TestConfig {

		@Bean
		ServletEndpointsSupplier servletEndpointsSupplier() {
			return Collections::emptyList;
		}

		@Bean
		DispatcherServletPath dispatcherServletPath() {
			return () -> "/test";
		}

		@Bean
		JerseyApplicationPath jerseyApplicationPath() {
			return () -> "/jersey";
		}

	}

}
