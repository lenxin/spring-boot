package org.springframework.boot.actuate.autoconfigure.metrics;

import io.micrometer.core.instrument.Meter;
import io.micrometer.core.instrument.MeterRegistry;

/**
 * Callback interface that can be used to customize auto-configured {@link MeterRegistry
 * MeterRegistries}.
 * <p>
 * Customizers are guaranteed to be applied before any {@link Meter} is registered with
 * the registry.
 *

 * @param <T> the registry type to customize
 * @since 2.0.0
 */
@FunctionalInterface
public interface MeterRegistryCustomizer<T extends MeterRegistry> {

	/**
	 * Customize the given {@code registry}.
	 * @param registry the registry to customize
	 */
	void customize(T registry);

}
