package org.springframework.boot.autoconfigure.cache;

import java.util.Properties;

import javax.cache.CacheManager;
import javax.cache.spi.CachingProvider;

/**
 * Callback interface that can be implemented by beans wishing to customize the properties
 * used by the {@link CachingProvider} to create the {@link CacheManager}.
 *

 */
interface JCachePropertiesCustomizer {

	/**
	 * Customize the properties.
	 * @param cacheProperties the cache properties
	 * @param properties the current properties
	 * @see CachingProvider#getCacheManager(java.net.URI, ClassLoader, Properties)
	 */
	void customize(CacheProperties cacheProperties, Properties properties);

}
