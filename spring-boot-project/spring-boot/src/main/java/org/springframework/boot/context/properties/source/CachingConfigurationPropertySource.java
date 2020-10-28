package org.springframework.boot.context.properties.source;

/**
 * Interface used to indicate that a {@link ConfigurationPropertySource} supports
 * {@link ConfigurationPropertyCaching}.
 *

 */
interface CachingConfigurationPropertySource {

	/**
	 * Return {@link ConfigurationPropertyCaching} for this source.
	 * @return source caching
	 */
	ConfigurationPropertyCaching getCaching();

	/**
	 * Find {@link ConfigurationPropertyCaching} for the given source.
	 * @param source the configuration property source
	 * @return a {@link ConfigurationPropertyCaching} instance or {@code null} if the
	 * source does not support caching.
	 */
	static ConfigurationPropertyCaching find(ConfigurationPropertySource source) {
		if (source instanceof CachingConfigurationPropertySource) {
			return ((CachingConfigurationPropertySource) source).getCaching();
		}
		return null;
	}

}
