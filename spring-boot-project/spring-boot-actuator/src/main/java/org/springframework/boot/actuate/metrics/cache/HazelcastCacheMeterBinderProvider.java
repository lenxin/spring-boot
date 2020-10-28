package org.springframework.boot.actuate.metrics.cache;

import java.lang.reflect.Method;

import com.hazelcast.spring.cache.HazelcastCache;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.binder.MeterBinder;
import io.micrometer.core.instrument.binder.cache.HazelcastCacheMetrics;

import org.springframework.util.ReflectionUtils;

/**
 * {@link CacheMeterBinderProvider} implementation for Hazelcast.
 *

 * @since 2.0.0
 */
public class HazelcastCacheMeterBinderProvider implements CacheMeterBinderProvider<HazelcastCache> {

	@Override
	public MeterBinder getMeterBinder(HazelcastCache cache, Iterable<Tag> tags) {
		try {
			return new HazelcastCacheMetrics(cache.getNativeCache(), tags);
		}
		catch (NoSuchMethodError ex) {
			// Hazelcast 4
			return createHazelcast4CacheMetrics(cache, tags);
		}
	}

	private MeterBinder createHazelcast4CacheMetrics(HazelcastCache cache, Iterable<Tag> tags) {
		try {
			Method nativeCacheAccessor = ReflectionUtils.findMethod(HazelcastCache.class, "getNativeCache");
			Object nativeCache = ReflectionUtils.invokeMethod(nativeCacheAccessor, cache);
			return HazelcastCacheMetrics.class.getConstructor(Object.class, Iterable.class).newInstance(nativeCache,
					tags);
		}
		catch (Exception ex) {
			throw new IllegalStateException("Failed to create MeterBinder for Hazelcast", ex);
		}
	}

}
