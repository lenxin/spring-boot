package org.springframework.boot.actuate.context.properties;

import org.junit.jupiter.api.Test;

import org.springframework.boot.actuate.context.properties.ConfigurationPropertiesReportEndpoint.ApplicationConfigurationProperties;
import org.springframework.boot.actuate.context.properties.ConfigurationPropertiesReportEndpoint.ConfigurationPropertiesBeanDescriptor;
import org.springframework.boot.actuate.context.properties.ConfigurationPropertiesReportEndpoint.ContextConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link ConfigurationPropertiesReportEndpoint} when used with bean methods.
 *


 */
class ConfigurationPropertiesReportEndpointMethodAnnotationsTests {

	@Test
	void testNaming() {
		ApplicationContextRunner contextRunner = new ApplicationContextRunner().withUserConfiguration(Config.class)
				.withPropertyValues("other.name:foo", "first.name:bar");
		contextRunner.run((context) -> {
			ConfigurationPropertiesReportEndpoint endpoint = context
					.getBean(ConfigurationPropertiesReportEndpoint.class);
			ApplicationConfigurationProperties applicationProperties = endpoint.configurationProperties();
			assertThat(applicationProperties.getContexts()).containsOnlyKeys(context.getId());
			ContextConfigurationProperties contextProperties = applicationProperties.getContexts().get(context.getId());
			ConfigurationPropertiesBeanDescriptor other = contextProperties.getBeans().get("other");
			assertThat(other).isNotNull();
			assertThat(other.getPrefix()).isEqualTo("other");
			assertThat(other.getProperties()).isNotNull();
			assertThat(other.getProperties()).isNotEmpty();
		});
	}

	@Test
	void prefixFromBeanMethodConfigurationPropertiesCanOverridePrefixOnClass() {
		ApplicationContextRunner contextRunner = new ApplicationContextRunner()
				.withUserConfiguration(OverriddenPrefix.class).withPropertyValues("other.name:foo");
		contextRunner.run((context) -> {
			ConfigurationPropertiesReportEndpoint endpoint = context
					.getBean(ConfigurationPropertiesReportEndpoint.class);
			ApplicationConfigurationProperties applicationProperties = endpoint.configurationProperties();
			assertThat(applicationProperties.getContexts()).containsOnlyKeys(context.getId());
			ContextConfigurationProperties contextProperties = applicationProperties.getContexts().get(context.getId());
			ConfigurationPropertiesBeanDescriptor bar = contextProperties.getBeans().get("bar");
			assertThat(bar).isNotNull();
			assertThat(bar.getPrefix()).isEqualTo("other");
			assertThat(bar.getProperties()).isNotNull();
			assertThat(bar.getProperties()).isNotEmpty();
		});
	}

	@Configuration(proxyBeanMethods = false)
	@EnableConfigurationProperties
	static class Config {

		@Bean
		ConfigurationPropertiesReportEndpoint endpoint() {
			return new ConfigurationPropertiesReportEndpoint();
		}

		@Bean
		@ConfigurationProperties(prefix = "first")
		Foo foo() {
			return new Foo();
		}

		@Bean
		@ConfigurationProperties(prefix = "other")
		Foo other() {
			return new Foo();
		}

	}

	@Configuration(proxyBeanMethods = false)
	@EnableConfigurationProperties
	static class OverriddenPrefix {

		@Bean
		ConfigurationPropertiesReportEndpoint endpoint() {
			return new ConfigurationPropertiesReportEndpoint();
		}

		@Bean
		@ConfigurationProperties(prefix = "other")
		Bar bar() {
			return new Bar();
		}

	}

	public static class Foo {

		private String name = "654321";

		public String getName() {
			return this.name;
		}

		public void setName(String name) {
			this.name = name;
		}

	}

	@ConfigurationProperties(prefix = "test")
	public static class Bar {

		private String name = "654321";

		public String getName() {
			return this.name;
		}

		public void setName(String name) {
			this.name = name;
		}

	}

}
