package org.springframework.boot.autoconfigure.cache;

import org.springframework.data.couchbase.cache.CouchbaseCacheManager;
import org.springframework.data.couchbase.cache.CouchbaseCacheManager.CouchbaseCacheManagerBuilder;

/**
 * Callback interface that can be implemented by beans wishing to customize the
 * {@link CouchbaseCacheManagerBuilder} before it is used to build the auto-configured
 * {@link CouchbaseCacheManager}.
 *

 * @since 2.3.3
 */
@FunctionalInterface
public interface CouchbaseCacheManagerBuilderCustomizer {

	/**
	 * Customize the {@link CouchbaseCacheManagerBuilder}.
	 * @param builder the builder to customize
	 */
	void customize(CouchbaseCacheManagerBuilder builder);

}
