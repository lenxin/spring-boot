package org.springframework.boot.actuate.metrics.cache;

import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.binder.MeterBinder;
import io.micrometer.core.instrument.binder.cache.EhCache2Metrics;

import org.springframework.cache.ehcache.EhCacheCache;

/**
 * {@link CacheMeterBinderProvider} implementation for EhCache2.
 *

 * @since 2.0.0
 */
public class EhCache2CacheMeterBinderProvider implements CacheMeterBinderProvider<EhCacheCache> {

	@Override
	public MeterBinder getMeterBinder(EhCacheCache cache, Iterable<Tag> tags) {
		return new EhCache2Metrics(cache.getNativeCache(), tags);
	}

}
