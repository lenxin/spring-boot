package org.springframework.boot.actuate.metrics.cache;

import java.util.Collections;

import com.github.benmanes.caffeine.cache.Caffeine;
import io.micrometer.core.instrument.binder.MeterBinder;
import io.micrometer.core.instrument.binder.cache.CaffeineCacheMetrics;
import org.junit.jupiter.api.Test;

import org.springframework.cache.caffeine.CaffeineCache;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link CaffeineCacheMeterBinderProvider}.
 *

 */
class CaffeineCacheMeterBinderProviderTests {

	@Test
	void caffeineCacheProvider() {
		CaffeineCache cache = new CaffeineCache("test", Caffeine.newBuilder().build());
		MeterBinder meterBinder = new CaffeineCacheMeterBinderProvider().getMeterBinder(cache, Collections.emptyList());
		assertThat(meterBinder).isInstanceOf(CaffeineCacheMetrics.class);
	}

}
