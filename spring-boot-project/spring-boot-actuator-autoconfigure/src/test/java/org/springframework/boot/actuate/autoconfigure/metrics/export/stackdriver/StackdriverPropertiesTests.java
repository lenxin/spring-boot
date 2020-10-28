package org.springframework.boot.actuate.autoconfigure.metrics.export.stackdriver;

import io.micrometer.stackdriver.StackdriverConfig;
import org.junit.jupiter.api.Test;

import org.springframework.boot.actuate.autoconfigure.metrics.export.properties.StepRegistryPropertiesTests;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link StackdriverProperties}.
 *

 */
class StackdriverPropertiesTests extends StepRegistryPropertiesTests {

	@Test
	void defaultValuesAreConsistent() {
		StackdriverProperties properties = new StackdriverProperties();
		StackdriverConfig config = (key) -> null;
		assertStepRegistryDefaultValues(properties, config);
		assertThat(properties.getResourceType()).isEqualTo(config.resourceType());
	}

}
