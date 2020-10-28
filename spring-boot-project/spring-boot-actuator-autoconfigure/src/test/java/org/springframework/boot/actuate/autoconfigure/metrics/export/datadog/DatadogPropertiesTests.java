package org.springframework.boot.actuate.autoconfigure.metrics.export.datadog;

import io.micrometer.datadog.DatadogConfig;
import org.junit.jupiter.api.Test;

import org.springframework.boot.actuate.autoconfigure.metrics.export.properties.StepRegistryPropertiesTests;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link DatadogProperties}.
 *

 */
class DatadogPropertiesTests extends StepRegistryPropertiesTests {

	@Test
	void defaultValuesAreConsistent() {
		DatadogProperties properties = new DatadogProperties();
		DatadogConfig config = (key) -> null;
		assertStepRegistryDefaultValues(properties, config);
		assertThat(properties.isDescriptions()).isEqualTo(config.descriptions());
		assertThat(properties.getHostTag()).isEqualTo(config.hostTag());
		assertThat(properties.getUri()).isEqualTo(config.uri());
	}

}
