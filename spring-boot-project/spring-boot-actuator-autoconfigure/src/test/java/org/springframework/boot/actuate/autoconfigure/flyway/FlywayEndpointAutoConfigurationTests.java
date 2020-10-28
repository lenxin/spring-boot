package org.springframework.boot.actuate.autoconfigure.flyway;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.Test;

import org.springframework.boot.actuate.flyway.FlywayEndpoint;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

/**
 * Tests for {@link FlywayEndpointAutoConfiguration}.
 *

 */
class FlywayEndpointAutoConfigurationTests {

	private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
			.withConfiguration(AutoConfigurations.of(FlywayEndpointAutoConfiguration.class))
			.withBean(Flyway.class, () -> mock(Flyway.class));

	@Test
	void runShouldHaveEndpointBean() {
		this.contextRunner.withPropertyValues("management.endpoints.web.exposure.include=flyway")
				.run((context) -> assertThat(context).hasSingleBean(FlywayEndpoint.class));
	}

	@Test
	void runWhenEnabledPropertyIsFalseShouldNotHaveEndpointBean() {
		this.contextRunner.withPropertyValues("management.endpoint.flyway.enabled:false")
				.run((context) -> assertThat(context).doesNotHaveBean(FlywayEndpoint.class));
	}

	@Test
	void runWhenNotExposedShouldNotHaveEndpointBean() {
		this.contextRunner.run((context) -> assertThat(context).doesNotHaveBean(FlywayEndpoint.class));
	}

}
