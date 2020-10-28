package org.springframework.boot.actuate.autoconfigure.web.jersey;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import org.junit.jupiter.api.Test;

import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.autoconfigure.web.servlet.JerseyApplicationPath;
import org.springframework.boot.test.context.FilteredClassLoader;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.boot.test.context.runner.WebApplicationContextRunner;
import org.springframework.boot.testsupport.classpath.ClassPathExclusions;
import org.springframework.boot.web.servlet.ServletRegistrationBean;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link JerseyChildManagementContextConfiguration}.
 *


 */
@ClassPathExclusions("spring-webmvc-*")
class JerseyChildManagementContextConfigurationTests {

	private final WebApplicationContextRunner contextRunner = new WebApplicationContextRunner()
			.withUserConfiguration(JerseyChildManagementContextConfiguration.class);

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
	void jerseyApplicationPathIsAutoConfigured() {
		this.contextRunner.run((context) -> {
			JerseyApplicationPath bean = context.getBean(JerseyApplicationPath.class);
			assertThat(bean.getPath()).isEqualTo("/");
		});
	}

	@Test
	@SuppressWarnings("unchecked")
	void servletRegistrationBeanIsAutoConfigured() {
		this.contextRunner.run((context) -> {
			ServletRegistrationBean<ServletContainer> bean = context.getBean(ServletRegistrationBean.class);
			assertThat(bean.getUrlMappings()).containsExactly("/*");
		});
	}

	@Test
	void resourceConfigCustomizerBeanIsNotRequired() {
		this.contextRunner.run((context) -> assertThat(context).hasSingleBean(ResourceConfig.class));
	}

}
