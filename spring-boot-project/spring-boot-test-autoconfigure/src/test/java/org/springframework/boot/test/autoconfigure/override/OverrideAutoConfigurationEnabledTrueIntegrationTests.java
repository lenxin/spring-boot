package org.springframework.boot.test.autoconfigure.override;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationPropertiesBindingPostProcessor;
import org.springframework.boot.test.autoconfigure.ExampleTestConfig;
import org.springframework.boot.test.autoconfigure.OverrideAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTestContextBootstrapper;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.BootstrapWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for {@link OverrideAutoConfiguration @OverrideAutoConfiguration} when
 * {@code enabled} is {@code true}.
 *

 */
@ExtendWith(SpringExtension.class)
@OverrideAutoConfiguration(enabled = true)
@BootstrapWith(SpringBootTestContextBootstrapper.class)
@ImportAutoConfiguration(ExampleTestConfig.class)
class OverrideAutoConfigurationEnabledTrueIntegrationTests {

	@Autowired
	private ApplicationContext context;

	@Test
	void autoConfiguredContext() {
		ApplicationContext context = this.context;
		assertThat(context.getBean(OverrideAutoConfigurationSpringBootApplication.class)).isNotNull();
		assertThat(context.getBean(ConfigurationPropertiesBindingPostProcessor.class)).isNotNull();
	}

}
