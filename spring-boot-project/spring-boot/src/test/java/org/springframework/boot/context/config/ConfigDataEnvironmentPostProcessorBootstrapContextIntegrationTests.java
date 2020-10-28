package org.springframework.boot.context.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.boot.BootstrapRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.context.config.TestConfigDataBootstrap.LoaderHelper;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for {@link ConfigDataEnvironmentPostProcessor} when used with a
 * {@link BootstrapRegistry}.
 *

 */
class ConfigDataEnvironmentPostProcessorBootstrapContextIntegrationTests {

	private SpringApplication application;

	@BeforeEach
	void setup() {
		this.application = new SpringApplication(Config.class);
		this.application.setWebApplicationType(WebApplicationType.NONE);
	}

	@Test
	void bootstrapsApplicationContext() {
		try (ConfigurableApplicationContext context = this.application
				.run("--spring.config.import=classpath:application-bootstrap-registry-integration-tests.properties")) {
			LoaderHelper bean = context.getBean(TestConfigDataBootstrap.LoaderHelper.class);
			assertThat(bean).isNotNull();
			assertThat(bean.getBound()).isEqualTo("igotbound");
			assertThat(bean.getLocation().getResolverHelper().getLocation())
					.isEqualTo(ConfigDataLocation.of("testbootstrap:test"));
		}
	}

	@Configuration
	static class Config {

	}

}
