package org.springframework.boot;

import java.util.Map;
import java.util.function.Consumer;

import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.util.CollectionUtils;

/**
 * {@link MapPropertySource} containing default properties contributed directly to a
 * {@code SpringApplication}. By convention, the {@link DefaultPropertiesPropertySource}
 * is always the last property source in the {@link Environment}.
 *

 * @since 2.4.0
 */
public class DefaultPropertiesPropertySource extends MapPropertySource {

	/**
	 * The name of the 'default properties' property source.
	 */
	public static final String NAME = "defaultProperties";

	/**
	 * Create a new {@link DefaultPropertiesPropertySource} with the given {@code Map}
	 * source.
	 * @param source the source map
	 */
	public DefaultPropertiesPropertySource(Map<String, Object> source) {
		super(NAME, source);
	}

	/**
	 * Return {@code true} if the given source is named 'defaultProperties'.
	 * @param propertySource the property source to check
	 * @return {@code true} if the name matches
	 */
	public static boolean hasMatchingName(PropertySource<?> propertySource) {
		return (propertySource != null) && propertySource.getName().equals(NAME);
	}

	/**
	 * Create a consume a new {@link DefaultPropertiesPropertySource} instance if the
	 * provided source is not empty.
	 * @param source the {@code Map} source
	 * @param action the action used to consume the
	 * {@link DefaultPropertiesPropertySource}
	 */
	public static void ifNotEmpty(Map<String, Object> source, Consumer<DefaultPropertiesPropertySource> action) {
		if (!CollectionUtils.isEmpty(source) && action != null) {
			action.accept(new DefaultPropertiesPropertySource(source));
		}
	}

	/**
	 * Move the 'defaultProperties' property source so that it's the last source in the
	 * given {@link ConfigurableEnvironment}.
	 * @param environment the environment to update
	 */
	public static void moveToEnd(ConfigurableEnvironment environment) {
		moveToEnd(environment.getPropertySources());
	}

	/**
	 * Move the 'defaultProperties' property source so that it's the last source in the
	 * given {@link MutablePropertySources}.
	 * @param propertySources the property sources to update
	 */
	public static void moveToEnd(MutablePropertySources propertySources) {
		PropertySource<?> propertySource = propertySources.remove(NAME);
		if (propertySource != null) {
			propertySources.addLast(propertySource);
		}
	}

}
