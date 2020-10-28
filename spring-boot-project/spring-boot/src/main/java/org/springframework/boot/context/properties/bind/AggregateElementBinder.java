package org.springframework.boot.context.properties.bind;

import org.springframework.boot.context.properties.source.ConfigurationPropertyName;
import org.springframework.boot.context.properties.source.ConfigurationPropertySource;

/**
 * Binder that can be used by {@link AggregateBinder} implementations to recursively bind
 * elements.
 *


 */
@FunctionalInterface
interface AggregateElementBinder {

	/**
	 * Bind the given name to a target bindable.
	 * @param name the name to bind
	 * @param target the target bindable
	 * @return a bound object or {@code null}
	 */
	default Object bind(ConfigurationPropertyName name, Bindable<?> target) {
		return bind(name, target, null);
	}

	/**
	 * Bind the given name to a target bindable using optionally limited to a single
	 * source.
	 * @param name the name to bind
	 * @param target the target bindable
	 * @param source the source of the elements or {@code null} to use all sources
	 * @return a bound object or {@code null}
	 */
	Object bind(ConfigurationPropertyName name, Bindable<?> target, ConfigurationPropertySource source);

}
