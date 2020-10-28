package org.springframework.boot.actuate.autoconfigure.metrics.export.properties;

import java.time.Duration;

import io.micrometer.core.instrument.push.PushRegistryConfig;

/**
 * Base class for {@link PushRegistryProperties} to {@link PushRegistryConfig} adapters.
 *
 * @param <T> the properties type



 * @since 2.2.0
 */
public abstract class PushRegistryPropertiesConfigAdapter<T extends PushRegistryProperties>
		extends PropertiesConfigAdapter<T> implements PushRegistryConfig {

	public PushRegistryPropertiesConfigAdapter(T properties) {
		super(properties);
	}

	@Override
	public String get(String k) {
		return null;
	}

	@Override
	public Duration step() {
		return get(T::getStep, PushRegistryConfig.super::step);
	}

	@Override
	public boolean enabled() {
		return get(T::isEnabled, PushRegistryConfig.super::enabled);
	}

	@Override
	public int batchSize() {
		return get(T::getBatchSize, PushRegistryConfig.super::batchSize);
	}

}
