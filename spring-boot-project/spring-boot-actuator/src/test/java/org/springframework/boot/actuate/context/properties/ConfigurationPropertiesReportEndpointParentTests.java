package org.springframework.boot.actuate.context.properties;

import org.junit.jupiter.api.Test;

import org.springframework.boot.actuate.context.properties.ConfigurationPropertiesReportEndpoint.ApplicationConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link ConfigurationPropertiesReportEndpoint} when used with a parent
 * context.
 *


 */
class ConfigurationPropertiesReportEndpointParentTests {

	@Test
	void configurationPropertiesClass() {
		new ApplicationContextRunner().withUserConfiguration(Parent.class).run((parent) -> {
			new ApplicationContextRunner().withUserConfiguration(ClassConfigurationProperties.class).withParent(parent)
					.run((child) -> {
						ConfigurationPropertiesReportEndpoint endpoint = child
								.getBean(ConfigurationPropertiesReportEndpoint.class);
						ApplicationConfigurationProperties applicationProperties = endpoint.configurationProperties();
						assertThat(applicationProperties.getContexts()).containsOnlyKeys(child.getId(), parent.getId());
						assertThat(applicationProperties.getContexts().get(child.getId()).getBeans().keySet())
								.containsExactly("someProperties");
						assertThat((applicationProperties.getContexts().get(parent.getId()).getBeans().keySet()))
								.containsExactly("testProperties");
					});
		});
	}

	@Test
	void configurationPropertiesBeanMethod() {
		new ApplicationContextRunner().withUserConfiguration(Parent.class).run((parent) -> {
			new ApplicationContextRunner().withUserConfiguration(BeanMethodConfigurationProperties.class)
					.withParent(parent).run((child) -> {
						ConfigurationPropertiesReportEndpoint endpoint = child
								.getBean(ConfigurationPropertiesReportEndpoint.class);
						ApplicationConfigurationProperties applicationProperties = endpoint.configurationProperties();
						assertThat(applicationProperties.getContexts().get(child.getId()).getBeans().keySet())
								.containsExactlyInAnyOrder("otherProperties");
						assertThat((applicationProperties.getContexts().get(parent.getId()).getBeans().keySet()))
								.containsExactly("testProperties");
					});
		});
	}

	@Configuration(proxyBeanMethods = false)
	@EnableConfigurationProperties
	static class Parent {

		@Bean
		TestProperties testProperties() {
			return new TestProperties();
		}

	}

	@Configuration(proxyBeanMethods = false)
	@EnableConfigurationProperties
	static class ClassConfigurationProperties {

		@Bean
		ConfigurationPropertiesReportEndpoint endpoint() {
			return new ConfigurationPropertiesReportEndpoint();
		}

		@Bean
		TestProperties someProperties() {
			return new TestProperties();
		}

	}

	@Configuration(proxyBeanMethods = false)
	@EnableConfigurationProperties
	static class BeanMethodConfigurationProperties {

		@Bean
		ConfigurationPropertiesReportEndpoint endpoint() {
			return new ConfigurationPropertiesReportEndpoint();
		}

		@Bean
		@ConfigurationProperties(prefix = "other")
		OtherProperties otherProperties() {
			return new OtherProperties();
		}

	}

	static class OtherProperties {

	}

	@ConfigurationProperties(prefix = "test")
	static class TestProperties {

		private String myTestProperty = "654321";

		String getMyTestProperty() {
			return this.myTestProperty;
		}

		void setMyTestProperty(String myTestProperty) {
			this.myTestProperty = myTestProperty;
		}

	}

}
