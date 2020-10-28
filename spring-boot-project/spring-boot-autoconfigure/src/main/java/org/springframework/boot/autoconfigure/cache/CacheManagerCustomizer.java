package org.springframework.boot.autoconfigure.cache;

import org.springframework.cache.CacheManager;

/**
 * Callback interface that can be implemented by beans wishing to customize the cache
 * manager before it is fully initialized, in particular to tune its configuration.
 *
 * @param <T> the type of the {@link CacheManager}

 * @since 1.3.3
 */
@FunctionalInterface
public interface CacheManagerCustomizer<T extends CacheManager> {

	/**
	 * Customize the cache manager.
	 * @param cacheManager the {@code CacheManager} to customize
	 */
	void customize(T cacheManager);

}
