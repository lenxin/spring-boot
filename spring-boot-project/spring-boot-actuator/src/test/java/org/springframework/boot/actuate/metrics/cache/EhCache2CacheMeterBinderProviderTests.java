package org.springframework.boot.actuate.metrics.cache;

import java.util.Collections;

import io.micrometer.core.instrument.binder.MeterBinder;
import io.micrometer.core.instrument.binder.cache.EhCache2Metrics;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.config.Configuration;
import org.junit.jupiter.api.Test;

import org.springframework.cache.ehcache.EhCacheCache;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link EhCache2CacheMeterBinderProvider}.
 *

 */
class EhCache2CacheMeterBinderProviderTests {

	@Test
	void ehCache2CacheProvider() {
		CacheManager cacheManager = new CacheManager(
				new Configuration().name("EhCacheCacheTests").defaultCache(new CacheConfiguration("default", 100)));
		try {
			Cache nativeCache = new Cache(new CacheConfiguration("test", 100));
			cacheManager.addCache(nativeCache);
			EhCacheCache cache = new EhCacheCache(nativeCache);
			MeterBinder meterBinder = new EhCache2CacheMeterBinderProvider().getMeterBinder(cache,
					Collections.emptyList());
			assertThat(meterBinder).isInstanceOf(EhCache2Metrics.class);
		}
		finally {
			cacheManager.shutdown();
		}
	}

}
