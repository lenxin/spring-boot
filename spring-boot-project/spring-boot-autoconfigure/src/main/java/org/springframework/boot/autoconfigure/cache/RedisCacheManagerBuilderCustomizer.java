package org.springframework.boot.autoconfigure.cache;

import org.springframework.data.redis.cache.RedisCacheManager.RedisCacheManagerBuilder;

/**
 * Callback interface that can be used to customize a {@link RedisCacheManagerBuilder}.
 *

 * @since 2.2.0
 */
@FunctionalInterface
public interface RedisCacheManagerBuilderCustomizer {

	/**
	 * Customize the {@link RedisCacheManagerBuilder}.
	 * @param builder the builder to customize
	 */
	void customize(RedisCacheManagerBuilder builder);

}
