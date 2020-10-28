package org.springframework.boot.actuate.autoconfigure.web.jersey;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import org.junit.jupiter.api.Test;

import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.autoconfigure.web.servlet.DefaultJerseyApplicationPath;
import org.springframework.boot.autoconfigure.web.servlet.JerseyApplicationPath;
import org.springframework.boot.test.context.FilteredClassLoader;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.boot.test.context.runner.WebApplicationContextRunner;
import org.springframework.boot.testsupport.classpath.ClassPathExclusions;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

/**
 * Tests for {@link JerseySameManagementContextConfiguration}.
 *

 */
@ClassPathExclusions("spring-webmvc-*")
class JerseySameManagementContextConfigurationTests {

	private final WebApplicationContextRunner contextRunner = new WebApplicationContextRunner()
			.withConfiguration(AutoConfigurations.of(JerseySameManagementContextConfiguration.class));

	@Test
	void autoConfigurationIsConditionalOnServletWebApplication() {
		ApplicationContextRunner contextRunner = new ApplicationContextRunner()
				.withConfiguration(AutoConfigurations.of(JerseySameManagementContextConfiguration.class));
		contextRunner
				.run((context) -> assertThat(context).doesNotHaveBean(JerseySameManagementContextConfiguration.class));
	}

	@Test
	void autoConfigurationIsConditionalOnClassResourceConfig() {
		this.contextRunner.withClassLoader(new FilteredClassLoader(ResourceConfig.class))
				.run((context) -> assertThat(context).doesNotHaveBean(JerseySameManagementContextConfiguration.class));
	}

	@Test
	void jerseyApplicationPathIsAutoConfiguredWhenNeeded() {
		this.contextRunner.run((context) -> assertThat(context).hasSingleBean(DefaultJerseyApplicationPath.class));
	}

	@Test
	void jerseyApplicationPathIsConditionalOnMissingBean() {
		this.contextRunner.withUserConfiguration(ConfigWithJerseyApplicationPath.class).run((context) -> {
			assertThat(context).hasSingleBean(JerseyApplicationPath.class);
			assertThat(context).hasBean("testJerseyApplicationPath");
		});
	}

	@Test
	void existingResourceConfigBeanShouldNotAutoConfigureRelatedBeans() {
		this.contextRunner.withUserConfiguration(ConfigWithResourceConfig.class).run((context) -> {
			assertThat(context).hasSingleBean(ResourceConfig.class);
			assertThat(context).doesNotHaveBean(JerseyApplicationPath.class);
			assertThat(context).doesNotHaveBean(ServletRegistrationBean.class);
			assertThat(context).hasBean("customResourceConfig");
		});
	}

	@Test
	@SuppressWarnings("unchecked")
	void servletRegistrationBeanIsAutoConfiguredWhenNeeded() {
		this.contextRunner.withPropertyValues("spring.jersey.application-path=/jersey").run((context) -> {
			ServletRegistrationBean<ServletContainer> bean = context.getBean(ServletRegistrationBean.class);
			assertThat(bean.getUrlMappings()).containsExactly("/jersey/*");
		});
	}

	@Configuration(proxyBeanMethods = false)
	static class ConfigWithJerseyApplicationPath {

		@Bean
		JerseyApplicationPath testJerseyApplicationPath() {
			return mock(JerseyApplicationPath.class);
		}

	}

	@Configuration(proxyBeanMethods = false)
	static class ConfigWithResourceConfig {

		@Bean
		ResourceConfig customResourceConfig() {
			return new ResourceConfig();
		}

	}

}
