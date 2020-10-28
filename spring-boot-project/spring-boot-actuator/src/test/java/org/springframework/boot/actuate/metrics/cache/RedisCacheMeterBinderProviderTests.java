package org.springframework.boot.actuate.metrics.cache;

import java.util.Collections;

import io.micrometer.core.instrument.binder.MeterBinder;
import org.junit.jupiter.api.Test;

import org.springframework.data.redis.cache.RedisCache;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

/**
 * Tests for {@link RedisCacheMeterBinderProvider}.
 *

 */
class RedisCacheMeterBinderProviderTests {

	@Test
	void redisCacheProvider() {
		RedisCache cache = mock(RedisCache.class);
		given(cache.getName()).willReturn("test");
		MeterBinder meterBinder = new RedisCacheMeterBinderProvider().getMeterBinder(cache, Collections.emptyList());
		assertThat(meterBinder).isInstanceOf(RedisCacheMetrics.class);
	}

}
