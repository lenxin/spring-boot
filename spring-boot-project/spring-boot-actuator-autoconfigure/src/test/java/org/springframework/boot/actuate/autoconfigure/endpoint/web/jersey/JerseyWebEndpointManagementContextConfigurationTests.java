package org.springframework.boot.actuate.autoconfigure.endpoint.web.jersey;

import java.util.Collections;

import org.glassfish.jersey.server.ResourceConfig;
import org.junit.jupiter.api.Test;

import org.springframework.boot.actuate.autoconfigure.endpoint.web.WebEndpointAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.endpoint.web.jersey.JerseyWebEndpointManagementContextConfiguration.JerseyWebEndpointsResourcesRegistrar;
import org.springframework.boot.actuate.autoconfigure.web.jersey.JerseySameManagementContextConfiguration;
import org.springframework.boot.actuate.endpoint.web.WebEndpointsSupplier;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.FilteredClassLoader;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.boot.test.context.runner.WebApplicationContextRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link JerseyWebEndpointManagementContextConfiguration}.
 *


 */
class JerseyWebEndpointManagementContextConfigurationTests {

	private final WebApplicationContextRunner runner = new WebApplicationContextRunner()
			.withConfiguration(AutoConfigurations.of(WebEndpointAutoConfiguration.class,
					JerseyWebEndpointManagementContextConfiguration.class))
			.withBean(WebEndpointsSupplier.class, () -> Collections::emptyList);

	@Test
	void jerseyWebEndpointsResourcesRegistrarForEndpointsIsAutoConfigured() {
		this.runner.run((context) -> assertThat(context).hasSingleBean(JerseyWebEndpointsResourcesRegistrar.class));
	}

	@Test
	void autoConfigurationIsConditionalOnServletWebApplication() {
		ApplicationContextRunner contextRunner = new ApplicationContextRunner()
				.withConfiguration(AutoConfigurations.of(JerseySameManagementContextConfiguration.class));
		contextRunner
				.run((context) -> assertThat(context).doesNotHaveBean(JerseySameManagementContextConfiguration.class));
	}

	@Test
	void autoConfigurationIsConditionalOnClassResourceConfig() {
		this.runner.withClassLoader(new FilteredClassLoader(ResourceConfig.class))
				.run((context) -> assertThat(context).doesNotHaveBean(JerseySameManagementContextConfiguration.class));
	}

}
