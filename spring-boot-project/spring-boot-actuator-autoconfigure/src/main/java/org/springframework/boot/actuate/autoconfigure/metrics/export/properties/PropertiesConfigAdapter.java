package org.springframework.boot.actuate.autoconfigure.metrics.export.properties;

import java.util.function.Function;
import java.util.function.Supplier;

import org.springframework.util.Assert;

/**
 * Base class for properties to config adapters.
 *
 * @param <T> the properties type


 * @since 2.0.0
 */
public class PropertiesConfigAdapter<T> {

	private T properties;

	/**
	 * Create a new {@link PropertiesConfigAdapter} instance.
	 * @param properties the source properties
	 */
	public PropertiesConfigAdapter(T properties) {
		Assert.notNull(properties, "Properties must not be null");
		this.properties = properties;
	}

	/**
	 * Get the value from the properties or use a fallback from the {@code defaults}.
	 * @param getter the getter for the properties
	 * @param fallback the fallback method, usually super interface method reference
	 * @param <V> the value type
	 * @return the property or fallback value
	 */
	protected final <V> V get(Function<T, V> getter, Supplier<V> fallback) {
		V value = getter.apply(this.properties);
		return (value != null) ? value : fallback.get();
	}

}
