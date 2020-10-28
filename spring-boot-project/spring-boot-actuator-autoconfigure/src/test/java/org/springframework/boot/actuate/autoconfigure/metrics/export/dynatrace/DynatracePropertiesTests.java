package org.springframework.boot.actuate.autoconfigure.metrics.export.dynatrace;

import io.micrometer.dynatrace.DynatraceConfig;
import org.junit.jupiter.api.Test;

import org.springframework.boot.actuate.autoconfigure.metrics.export.properties.StepRegistryPropertiesTests;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link DynatraceProperties}.
 *

 */
class DynatracePropertiesTests extends StepRegistryPropertiesTests {

	@Test
	void defaultValuesAreConsistent() {
		DynatraceProperties properties = new DynatraceProperties();
		DynatraceConfig config = (key) -> null;
		assertStepRegistryDefaultValues(properties, config);
		assertThat(properties.getTechnologyType()).isEqualTo(config.technologyType());
	}

}
