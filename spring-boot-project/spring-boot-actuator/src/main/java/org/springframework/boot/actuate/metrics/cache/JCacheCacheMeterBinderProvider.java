package org.springframework.boot.actuate.metrics.cache;

import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.binder.MeterBinder;
import io.micrometer.core.instrument.binder.cache.JCacheMetrics;

import org.springframework.cache.jcache.JCacheCache;

/**
 * {@link CacheMeterBinderProvider} implementation for JCache.
 *

 * @since 2.0.0
 */
public class JCacheCacheMeterBinderProvider implements CacheMeterBinderProvider<JCacheCache> {

	@Override
	public MeterBinder getMeterBinder(JCacheCache cache, Iterable<Tag> tags) {
		return new JCacheMetrics(cache.getNativeCache(), tags);
	}

}
