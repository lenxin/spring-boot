package org.springframework.boot.actuate.cache;

import java.util.Collection;
import java.util.Collections;

/**
 * Exception thrown when multiple caches exist with the same name.
 *

 * @since 2.1.0
 */
public class NonUniqueCacheException extends RuntimeException {

	private final String cacheName;

	private final Collection<String> cacheManagerNames;

	public NonUniqueCacheException(String cacheName, Collection<String> cacheManagerNames) {
		super(String.format("Multiple caches named %s found, specify the 'cacheManager' to use: %s", cacheName,
				cacheManagerNames));
		this.cacheName = cacheName;
		this.cacheManagerNames = Collections.unmodifiableCollection(cacheManagerNames);
	}

	public String getCacheName() {
		return this.cacheName;
	}

	public Collection<String> getCacheManagerNames() {
		return this.cacheManagerNames;
	}

}
