package org.springframework.boot.actuate.metrics.cache;

import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.binder.MeterBinder;
import io.micrometer.core.instrument.binder.cache.CaffeineCacheMetrics;

import org.springframework.cache.caffeine.CaffeineCache;

/**
 * {@link CacheMeterBinderProvider} implementation for Caffeine.
 *

 * @since 2.0.0
 */
public class CaffeineCacheMeterBinderProvider implements CacheMeterBinderProvider<CaffeineCache> {

	@Override
	public MeterBinder getMeterBinder(CaffeineCache cache, Iterable<Tag> tags) {
		return new CaffeineCacheMetrics(cache.getNativeCache(), cache.getName(), tags);
	}

}
