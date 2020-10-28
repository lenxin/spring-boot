package org.springframework.boot.actuate.autoconfigure.context.properties;

import java.util.Map;

import org.junit.jupiter.api.Test;

import org.springframework.boot.actuate.context.properties.ConfigurationPropertiesReportEndpoint;
import org.springframework.boot.actuate.context.properties.ConfigurationPropertiesReportEndpoint.ApplicationConfigurationProperties;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.assertj.AssertableApplicationContext;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.boot.test.context.runner.ContextConsumer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link ConfigurationPropertiesReportEndpointAutoConfiguration}.
 *

 */
class ConfigurationPropertiesReportEndpointAutoConfigurationTests {

	private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
			.withConfiguration(AutoConfigurations.of(ConfigurationPropertiesReportEndpointAutoConfiguration.class));

	@Test
	void runShouldHaveEndpointBean() {
		this.contextRunner.withUserConfiguration(Config.class)
				.withPropertyValues("management.endpoints.web.exposure.include=configprops")
				.run(validateTestProperties("******", "654321"));
	}

	@Test
	void runWhenEnabledPropertyIsFalseShouldNotHaveEndpointBean() {
		this.contextRunner.withPropertyValues("management.endpoint.configprops.enabled:false")
				.run((context) -> assertThat(context).doesNotHaveBean(ConfigurationPropertiesReportEndpoint.class));
	}

	@Test
	void keysToSanitizeCanBeConfiguredViaTheEnvironment() {
		this.contextRunner.withUserConfiguration(Config.class)
				.withPropertyValues("management.endpoint.configprops.keys-to-sanitize: .*pass.*, property")
				.withPropertyValues("management.endpoints.web.exposure.include=configprops")
				.run(validateTestProperties("******", "******"));
	}

	@Test
	void runWhenNotExposedShouldNotHaveEndpointBean() {
		this.contextRunner
				.run((context) -> assertThat(context).doesNotHaveBean(ConfigurationPropertiesReportEndpoint.class));
	}

	private ContextConsumer<AssertableApplicationContext> validateTestProperties(String dbPassword,
			String myTestProperty) {
		return (context) -> {
			assertThat(context).hasSingleBean(ConfigurationPropertiesReportEndpoint.class);
			ConfigurationPropertiesReportEndpoint endpoint = context
					.getBean(ConfigurationPropertiesReportEndpoint.class);
			ApplicationConfigurationProperties properties = endpoint.configurationProperties();
			Map<String, Object> nestedProperties = properties.getContexts().get(context.getId()).getBeans()
					.get("testProperties").getProperties();
			assertThat(nestedProperties).isNotNull();
			assertThat(nestedProperties.get("dbPassword")).isEqualTo(dbPassword);
			assertThat(nestedProperties.get("myTestProperty")).isEqualTo(myTestProperty);
		};
	}

	@Configuration(proxyBeanMethods = false)
	@EnableConfigurationProperties
	static class Config {

		@Bean
		TestProperties testProperties() {
			return new TestProperties();
		}

	}

	@ConfigurationProperties("test")
	public static class TestProperties {

		private String dbPassword = "123456";

		private String myTestProperty = "654321";

		public String getDbPassword() {
			return this.dbPassword;
		}

		public void setDbPassword(String dbPassword) {
			this.dbPassword = dbPassword;
		}

		public String getMyTestProperty() {
			return this.myTestProperty;
		}

		public void setMyTestProperty(String myTestProperty) {
			this.myTestProperty = myTestProperty;
		}

	}

}
