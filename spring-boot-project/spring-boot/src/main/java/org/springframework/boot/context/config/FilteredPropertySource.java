package org.springframework.boot.context.config;

import java.util.Set;
import java.util.function.Consumer;

import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;

/**
 * Internal {@link PropertySource} implementation used by
 * {@link ConfigFileApplicationListener} to filter out properties for specific operations.
 *

 * @deprecated since 2.4.0 along with {@link ConfigFileApplicationListener}
 */
@Deprecated
class FilteredPropertySource extends PropertySource<PropertySource<?>> {

	private final Set<String> filteredProperties;

	FilteredPropertySource(PropertySource<?> original, Set<String> filteredProperties) {
		super(original.getName(), original);
		this.filteredProperties = filteredProperties;
	}

	@Override
	public Object getProperty(String name) {
		if (this.filteredProperties.contains(name)) {
			return null;
		}
		return getSource().getProperty(name);
	}

	static void apply(ConfigurableEnvironment environment, String propertySourceName, Set<String> filteredProperties,
			Consumer<PropertySource<?>> operation) {
		MutablePropertySources propertySources = environment.getPropertySources();
		PropertySource<?> original = propertySources.get(propertySourceName);
		if (original == null) {
			operation.accept(null);
			return;
		}
		propertySources.replace(propertySourceName, new FilteredPropertySource(original, filteredProperties));
		try {
			operation.accept(original);
		}
		finally {
			propertySources.replace(propertySourceName, original);
		}
	}

}
