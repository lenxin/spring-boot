package org.springframework.boot.context.config;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.logging.Log;

import org.springframework.boot.context.properties.source.ConfigurationProperty;
import org.springframework.boot.context.properties.source.ConfigurationPropertyName;
import org.springframework.boot.context.properties.source.ConfigurationPropertySource;

/**
 * Exception thrown if an invalid property is found when processing config data.
 *


 * @since 2.4.0
 */
public class InvalidConfigDataPropertyException extends ConfigDataException {

	private static final Map<ConfigurationPropertyName, ConfigurationPropertyName> WARNING;
	static {
		Map<ConfigurationPropertyName, ConfigurationPropertyName> warning = new LinkedHashMap<>();
		warning.put(ConfigurationPropertyName.of("spring.profiles"),
				ConfigurationPropertyName.of("spring.config.activate.on-profile"));
		WARNING = Collections.unmodifiableMap(warning);
	}

	private final ConfigurationProperty property;

	private final ConfigurationPropertyName replacement;

	private final ConfigDataResource location;

	InvalidConfigDataPropertyException(ConfigurationProperty property, ConfigurationPropertyName replacement,
			ConfigDataResource location) {
		super(getMessage(property, replacement, location), null);
		this.property = property;
		this.replacement = replacement;
		this.location = location;
	}

	/**
	 * Return source property that caused the exception.
	 * @return the invalid property
	 */
	public ConfigurationProperty getProperty() {
		return this.property;
	}

	/**
	 * Return the {@link ConfigDataResource} of the invalid property or {@code null} if
	 * the source was not loaded from {@link ConfigData}.
	 * @return the config data location or {@code null}
	 */
	public ConfigDataResource getLocation() {
		return this.location;
	}

	/**
	 * Return the replacement property that should be used instead or {@code null} if not
	 * replacement is available.
	 * @return the replacement property name
	 */
	public ConfigurationPropertyName getReplacement() {
		return this.replacement;
	}

	/**
	 * Throw a {@link InvalidConfigDataPropertyException} or log a warning if the given
	 * {@link ConfigDataEnvironmentContributor} contains any invalid property. A warning
	 * is logged if the property is still supported, but not recommended. An error is
	 * thrown if the property is completely unsupported.
	 * @param logger the logger to use for warnings
	 * @param contributor the contributor to check
	 */
	static void throwOrWarn(Log logger, ConfigDataEnvironmentContributor contributor) {
		ConfigurationPropertySource propertySource = contributor.getConfigurationPropertySource();
		if (propertySource != null) {
			WARNING.forEach((invalid, replacement) -> {
				ConfigurationProperty property = propertySource.getConfigurationProperty(invalid);
				if (property != null) {
					logger.warn(getMessage(property, replacement, contributor.getResource()));
				}
			});
		}
	}

	private static String getMessage(ConfigurationProperty property, ConfigurationPropertyName replacement,
			ConfigDataResource location) {
		StringBuilder message = new StringBuilder("Property '");
		message.append(property.getName());
		if (location != null) {
			message.append("' imported from location '");
			message.append(location);
		}
		message.append("' is invalid");
		if (replacement != null) {
			message.append(" and should be replaced with '");
			message.append(replacement);
			message.append("'");
		}
		if (property.getOrigin() != null) {
			message.append(" [origin: ");
			message.append(property.getOrigin());
			message.append("]");
		}
		return message.toString();
	}

}
