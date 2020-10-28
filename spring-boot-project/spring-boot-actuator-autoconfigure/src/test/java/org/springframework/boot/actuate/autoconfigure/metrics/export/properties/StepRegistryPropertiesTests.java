package org.springframework.boot.actuate.autoconfigure.metrics.export.properties;

import io.micrometer.core.instrument.step.StepRegistryConfig;

/**
 * Base tests for {@link StepRegistryProperties} implementation.
 *

 */
public abstract class StepRegistryPropertiesTests extends PushRegistryPropertiesTests {

	protected void assertStepRegistryDefaultValues(StepRegistryProperties properties, StepRegistryConfig config) {
		super.assertStepRegistryDefaultValues(properties, config);
	}

}
