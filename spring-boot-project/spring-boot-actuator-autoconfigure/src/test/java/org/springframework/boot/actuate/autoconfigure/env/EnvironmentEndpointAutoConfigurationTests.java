package org.springframework.boot.actuate.autoconfigure.env;

import java.util.Map;

import org.junit.jupiter.api.Test;

import org.springframework.boot.actuate.env.EnvironmentEndpoint;
import org.springframework.boot.actuate.env.EnvironmentEndpoint.EnvironmentDescriptor;
import org.springframework.boot.actuate.env.EnvironmentEndpoint.PropertySourceDescriptor;
import org.springframework.boot.actuate.env.EnvironmentEndpoint.PropertyValueDescriptor;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.assertj.AssertableApplicationContext;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.boot.test.context.runner.ContextConsumer;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link EnvironmentEndpointAutoConfiguration}.
 *

 */
class EnvironmentEndpointAutoConfigurationTests {

	private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
			.withConfiguration(AutoConfigurations.of(EnvironmentEndpointAutoConfiguration.class));

	@Test
	void runShouldHaveEndpointBean() {
		this.contextRunner.withPropertyValues("management.endpoints.web.exposure.include=env")
				.withSystemProperties("dbPassword=123456", "apiKey=123456")
				.run(validateSystemProperties("******", "******"));
	}

	@Test
	void runWhenEnabledPropertyIsFalseShouldNotHaveEndpointBean() {
		this.contextRunner.withPropertyValues("management.endpoint.env.enabled:false")
				.run((context) -> assertThat(context).doesNotHaveBean(EnvironmentEndpoint.class));
	}

	@Test
	void runWhenNotExposedShouldNotHaveEndpointBean() {
		this.contextRunner.run((context) -> assertThat(context).doesNotHaveBean(EnvironmentEndpoint.class));
	}

	@Test
	void keysToSanitizeCanBeConfiguredViaTheEnvironment() {
		this.contextRunner.withPropertyValues("management.endpoints.web.exposure.include=env")
				.withSystemProperties("dbPassword=123456", "apiKey=123456")
				.withPropertyValues("management.endpoint.env.keys-to-sanitize=.*pass.*")
				.run(validateSystemProperties("******", "123456"));
	}

	private ContextConsumer<AssertableApplicationContext> validateSystemProperties(String dbPassword, String apiKey) {
		return (context) -> {
			assertThat(context).hasSingleBean(EnvironmentEndpoint.class);
			EnvironmentEndpoint endpoint = context.getBean(EnvironmentEndpoint.class);
			EnvironmentDescriptor env = endpoint.environment(null);
			Map<String, PropertyValueDescriptor> systemProperties = getSource("systemProperties", env).getProperties();
			assertThat(systemProperties.get("dbPassword").getValue()).isEqualTo(dbPassword);
			assertThat(systemProperties.get("apiKey").getValue()).isEqualTo(apiKey);
		};
	}

	private PropertySourceDescriptor getSource(String name, EnvironmentDescriptor descriptor) {
		return descriptor.getPropertySources().stream().filter((source) -> name.equals(source.getName())).findFirst()
				.get();
	}

}
