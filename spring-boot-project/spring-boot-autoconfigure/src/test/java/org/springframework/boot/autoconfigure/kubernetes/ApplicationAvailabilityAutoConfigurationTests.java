package org.springframework.boot.autoconfigure.kubernetes;

import org.junit.jupiter.api.Test;

import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.autoconfigure.availability.ApplicationAvailabilityAutoConfiguration;
import org.springframework.boot.availability.ApplicationAvailability;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link ApplicationAvailabilityAutoConfiguration}
 *

 */
class ApplicationAvailabilityAutoConfigurationTests {

	private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
			.withConfiguration(AutoConfigurations.of(ApplicationAvailabilityAutoConfiguration.class));

	@Test
	void providerIsPresent() {
		this.contextRunner.run(((context) -> assertThat(context).hasSingleBean(ApplicationAvailability.class)));
	}

}
