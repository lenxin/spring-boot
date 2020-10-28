package org.springframework.boot.actuate.autoconfigure.metrics.export.signalfx;

import io.micrometer.signalfx.SignalFxConfig;
import org.junit.jupiter.api.Test;

import org.springframework.boot.actuate.autoconfigure.metrics.export.properties.StepRegistryPropertiesTests;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link SignalFxProperties}.
 *

 */
class SignalFxPropertiesTests extends StepRegistryPropertiesTests {

	@Test
	void defaultValuesAreConsistent() {
		SignalFxProperties properties = new SignalFxProperties();
		SignalFxConfig config = (key) -> null;
		assertStepRegistryDefaultValues(properties, config);
		// access token is mandatory
		assertThat(properties.getUri()).isEqualTo(config.uri());
		// source has no static default value
	}

}
