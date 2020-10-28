package org.springframework.boot.autoconfigure.cache;

import javax.cache.CacheManager;

/**
 * Callback interface that can be implemented by beans wishing to customize the cache
 * manager before it is used, in particular to create additional caches.
 *

 * @since 1.3.0
 */
@FunctionalInterface
public interface JCacheManagerCustomizer {

	/**
	 * Customize the cache manager.
	 * @param cacheManager the {@code javax.cache.CacheManager} to customize
	 */
	void customize(CacheManager cacheManager);

}
