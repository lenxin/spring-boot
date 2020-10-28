package org.springframework.boot.context.config;

import org.springframework.boot.context.properties.bind.PlaceholdersResolver;
import org.springframework.boot.origin.Origin;
import org.springframework.boot.origin.OriginLookup;
import org.springframework.core.env.PropertySource;
import org.springframework.util.PropertyPlaceholderHelper;
import org.springframework.util.SystemPropertyUtils;

/**
 * {@link PlaceholdersResolver} backed by one or more
 * {@link ConfigDataEnvironmentContributor} instances.
 *


 */
class ConfigDataEnvironmentContributorPlaceholdersResolver implements PlaceholdersResolver {

	private final Iterable<ConfigDataEnvironmentContributor> contributors;

	private final ConfigDataActivationContext activationContext;

	private final boolean failOnResolveFromInactiveContributor;

	private final PropertyPlaceholderHelper helper;

	ConfigDataEnvironmentContributorPlaceholdersResolver(Iterable<ConfigDataEnvironmentContributor> contributors,
			ConfigDataActivationContext activationContext, boolean failOnResolveFromInactiveContributor) {
		this.contributors = contributors;
		this.activationContext = activationContext;
		this.failOnResolveFromInactiveContributor = failOnResolveFromInactiveContributor;
		this.helper = new PropertyPlaceholderHelper(SystemPropertyUtils.PLACEHOLDER_PREFIX,
				SystemPropertyUtils.PLACEHOLDER_SUFFIX, SystemPropertyUtils.VALUE_SEPARATOR, true);
	}

	@Override
	public Object resolvePlaceholders(Object value) {
		if (value instanceof String) {
			return this.helper.replacePlaceholders((String) value, this::resolvePlaceholder);
		}
		return value;
	}

	private String resolvePlaceholder(String placeholder) {
		Object result = null;
		for (ConfigDataEnvironmentContributor contributor : this.contributors) {
			PropertySource<?> propertySource = contributor.getPropertySource();
			Object value = (propertySource != null) ? propertySource.getProperty(placeholder) : null;
			if (value != null && !contributor.isActive(this.activationContext)) {
				if (this.failOnResolveFromInactiveContributor) {
					ConfigDataResource resource = contributor.getResource();
					Origin origin = OriginLookup.getOrigin(propertySource, placeholder);
					throw new InactiveConfigDataAccessException(propertySource, resource, placeholder, origin);
				}
				value = null;
			}
			result = (result != null) ? result : value;
		}
		return (result != null) ? String.valueOf(result) : null;
	}

}
