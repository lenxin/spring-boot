package org.springframework.boot.context.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertySource;
import org.springframework.util.Assert;

/**
 * Configuration data that has been loaded from a {@link ConfigDataResource} and may
 * ultimately contribute {@link PropertySource property sources} to Spring's
 * {@link Environment}.
 *


 * @since 2.4.0
 * @see ConfigDataLocationResolver
 * @see ConfigDataLoader
 */
public final class ConfigData {

	private final List<PropertySource<?>> propertySources;

	private final Set<Option> options;

	/**
	 * Create a new {@link ConfigData} instance.
	 * @param propertySources the config data property sources in ascending priority
	 * order.
	 * @param options the config data options
	 */
	public ConfigData(Collection<? extends PropertySource<?>> propertySources, Option... options) {
		Assert.notNull(propertySources, "PropertySources must not be null");
		Assert.notNull(options, "Options must not be null");
		this.propertySources = Collections.unmodifiableList(new ArrayList<>(propertySources));
		this.options = Collections.unmodifiableSet(
				(options.length != 0) ? EnumSet.copyOf(Arrays.asList(options)) : EnumSet.noneOf(Option.class));
	}

	/**
	 * Return the configuration data property sources in ascending priority order. If the
	 * same key is contained in more than one of the sources, then the later source will
	 * win.
	 * @return the config data property sources
	 */
	public List<PropertySource<?>> getPropertySources() {
		return this.propertySources;
	}

	/**
	 * Return a set of {@link Option config data options} for this source.
	 * @return the config data options
	 */
	public Set<Option> getOptions() {
		return this.options;
	}

	/**
	 * Option flags that can be applied config data.
	 */
	public enum Option {

		/**
		 * Ignore all imports properties from the sources.
		 */
		IGNORE_IMPORTS;

	}

}
