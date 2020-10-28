package org.springframework.boot.actuate.autoconfigure.metrics.export.properties;

import io.micrometer.core.instrument.step.StepRegistryConfig;

/**
 * Base class for {@link StepRegistryProperties} to {@link StepRegistryConfig} adapters.
 *
 * @param <T> the properties type



 * @since 2.0.0
 */
public abstract class StepRegistryPropertiesConfigAdapter<T extends StepRegistryProperties>
		extends PushRegistryPropertiesConfigAdapter<T> {

	public StepRegistryPropertiesConfigAdapter(T properties) {
		super(properties);
	}

}
