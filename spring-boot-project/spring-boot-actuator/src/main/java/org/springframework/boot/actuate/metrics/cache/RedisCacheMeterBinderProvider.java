package org.springframework.boot.actuate.metrics.cache;

import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.binder.MeterBinder;

import org.springframework.data.redis.cache.RedisCache;

/**
 * {@link CacheMeterBinderProvider} implementation for Redis.
 *

 * @since 2.4.0
 */
public class RedisCacheMeterBinderProvider implements CacheMeterBinderProvider<RedisCache> {

	@Override
	public MeterBinder getMeterBinder(RedisCache cache, Iterable<Tag> tags) {
		return new RedisCacheMetrics(cache, tags);
	}

}
