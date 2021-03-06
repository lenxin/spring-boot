package org.springframework.boot.context.properties.source;

import java.util.Iterator;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.springframework.boot.origin.OriginTrackedValue;

/**
 * A {@link ConfigurationPropertySource} with a fully {@link Iterable} set of entries.
 * Implementations of this interface <strong>must</strong> be able to iterate over all
 * contained configuration properties. Any {@code non-null} result from
 * {@link #getConfigurationProperty(ConfigurationPropertyName)} must also have an
 * equivalent entry in the {@link #iterator() iterator}.
 *


 * @since 2.0.0
 * @see ConfigurationPropertyName
 * @see OriginTrackedValue
 * @see #getConfigurationProperty(ConfigurationPropertyName)
 * @see #iterator()
 * @see #stream()
 */
public interface IterableConfigurationPropertySource
		extends ConfigurationPropertySource, Iterable<ConfigurationPropertyName> {

	/**
	 * Return an iterator for the {@link ConfigurationPropertyName names} managed by this
	 * source.
	 * @return an iterator (never {@code null})
	 */
	@Override
	default Iterator<ConfigurationPropertyName> iterator() {
		return stream().iterator();
	}

	/**
	 * Returns a sequential {@code Stream} for the {@link ConfigurationPropertyName names}
	 * managed by this source.
	 * @return a stream of names (never {@code null})
	 */
	Stream<ConfigurationPropertyName> stream();

	@Override
	default ConfigurationPropertyState containsDescendantOf(ConfigurationPropertyName name) {
		return ConfigurationPropertyState.search(this, name::isAncestorOf);
	}

	@Override
	default IterableConfigurationPropertySource filter(Predicate<ConfigurationPropertyName> filter) {
		return new FilteredIterableConfigurationPropertiesSource(this, filter);
	}

	@Override
	default IterableConfigurationPropertySource withAliases(ConfigurationPropertyNameAliases aliases) {
		return new AliasedIterableConfigurationPropertySource(this, aliases);
	}

}
