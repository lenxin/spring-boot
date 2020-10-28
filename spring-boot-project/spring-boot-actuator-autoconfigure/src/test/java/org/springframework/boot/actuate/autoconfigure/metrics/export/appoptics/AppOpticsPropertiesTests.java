package org.springframework.boot.actuate.autoconfigure.metrics.export.appoptics;

import io.micrometer.appoptics.AppOpticsConfig;
import org.junit.jupiter.api.Test;

import org.springframework.boot.actuate.autoconfigure.metrics.export.properties.StepRegistryPropertiesTests;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link AppOpticsProperties}.
 *

 */
class AppOpticsPropertiesTests extends StepRegistryPropertiesTests {

	@Test
	void defaultValuesAreConsistent() {
		AppOpticsProperties properties = new AppOpticsProperties();
		AppOpticsConfig config = (key) -> null;
		assertStepRegistryDefaultValues(properties, config);
		assertThat(properties.getUri()).isEqualToIgnoringWhitespace(config.uri());
		assertThat(properties.getHostTag()).isEqualToIgnoringWhitespace(config.hostTag());
		assertThat(properties.isFloorTimes()).isEqualTo(config.floorTimes());
	}

}
