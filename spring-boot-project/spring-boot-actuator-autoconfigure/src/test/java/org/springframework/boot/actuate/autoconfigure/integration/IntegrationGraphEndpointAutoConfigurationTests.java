package org.springframework.boot.actuate.autoconfigure.integration;

import org.junit.jupiter.api.Test;

import org.springframework.boot.actuate.integration.IntegrationGraphEndpoint;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.autoconfigure.integration.IntegrationAutoConfiguration;
import org.springframework.boot.autoconfigure.jmx.JmxAutoConfiguration;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.integration.graph.IntegrationGraphServer;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link IntegrationGraphEndpointAutoConfiguration}.
 *


 */
class IntegrationGraphEndpointAutoConfigurationTests {

	private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
			.withConfiguration(AutoConfigurations.of(JmxAutoConfiguration.class, IntegrationAutoConfiguration.class,
					IntegrationGraphEndpointAutoConfiguration.class));

	@Test
	void runShouldHaveEndpointBean() {
		this.contextRunner.withPropertyValues("management.endpoints.web.exposure.include=integrationgraph")
				.run((context) -> assertThat(context).hasSingleBean(IntegrationGraphEndpoint.class));
	}

	@Test
	void runWhenEnabledPropertyIsFalseShouldNotHaveEndpointBean() {
		this.contextRunner.withPropertyValues("management.endpoint.integrationgraph.enabled:false").run((context) -> {
			assertThat(context).doesNotHaveBean(IntegrationGraphEndpoint.class);
			assertThat(context).doesNotHaveBean(IntegrationGraphServer.class);
		});
	}

	@Test
	void runWhenNotExposedShouldNotHaveEndpointBean() {
		this.contextRunner.run((context) -> {
			assertThat(context).doesNotHaveBean(IntegrationGraphEndpoint.class);
			assertThat(context).doesNotHaveBean(IntegrationGraphServer.class);
		});
	}

	@Test
	void runWhenSpringIntegrationIsNotEnabledShouldNotHaveEndpointBean() {
		ApplicationContextRunner noSpringIntegrationRunner = new ApplicationContextRunner()
				.withConfiguration(AutoConfigurations.of(IntegrationGraphEndpointAutoConfiguration.class));
		noSpringIntegrationRunner.run((context) -> {
			assertThat(context).doesNotHaveBean(IntegrationGraphEndpoint.class);
			assertThat(context).doesNotHaveBean(IntegrationGraphServer.class);
		});
	}

}
