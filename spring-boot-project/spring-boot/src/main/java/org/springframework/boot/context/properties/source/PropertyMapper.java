package org.springframework.boot.context.properties.source;

import java.util.List;
import java.util.function.BiPredicate;

import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.PropertySource;

/**
 * Strategy used to provide a mapping between a {@link PropertySource} and a
 * {@link ConfigurationPropertySource}.
 * <p>
 * Mappings should be provided for both {@link ConfigurationPropertyName
 * ConfigurationPropertyName} types and {@code String} based names. This allows the
 * {@link SpringConfigurationPropertySource} to first attempt any direct mappings (i.e.
 * map the {@link ConfigurationPropertyName} directly to the {@link PropertySource} name)
 * before falling back to {@link EnumerablePropertySource enumerating} property names,
 * mapping them to a {@link ConfigurationPropertyName} and checking for applicability. See
 * {@link SpringConfigurationPropertySource} for more details.
 *


 * @see SpringConfigurationPropertySource
 */
interface PropertyMapper {

	/**
	 * The default ancestor of check.
	 */
	BiPredicate<ConfigurationPropertyName, ConfigurationPropertyName> DEFAULT_ANCESTOR_OF_CHECK = ConfigurationPropertyName::isAncestorOf;

	/**
	 * Provide mappings from a {@link ConfigurationPropertySource}
	 * {@link ConfigurationPropertyName}.
	 * @param configurationPropertyName the name to map
	 * @return the mapped names or an empty list
	 */
	List<String> map(ConfigurationPropertyName configurationPropertyName);

	/**
	 * Provide mappings from a {@link PropertySource} property name.
	 * @param propertySourceName the name to map
	 * @return the mapped configuration property name or
	 * {@link ConfigurationPropertyName#EMPTY}
	 */
	ConfigurationPropertyName map(String propertySourceName);

	/**
	 * Returns a {@link BiPredicate} that can be used to check if one name is an ancestor
	 * of another when considering the mapping rules.
	 * @return a predicate that can be used to check if one name is an ancestor of another
	 */
	default BiPredicate<ConfigurationPropertyName, ConfigurationPropertyName> getAncestorOfCheck() {
		return DEFAULT_ANCESTOR_OF_CHECK;
	}

}
