package org.springframework.boot.actuate.autoconfigure.metrics.cache;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.spring.cache.HazelcastCache;
import io.micrometer.core.instrument.binder.MeterBinder;
import net.sf.ehcache.Ehcache;

import org.springframework.boot.actuate.metrics.cache.CacheMeterBinderProvider;
import org.springframework.boot.actuate.metrics.cache.CaffeineCacheMeterBinderProvider;
import org.springframework.boot.actuate.metrics.cache.EhCache2CacheMeterBinderProvider;
import org.springframework.boot.actuate.metrics.cache.HazelcastCacheMeterBinderProvider;
import org.springframework.boot.actuate.metrics.cache.JCacheCacheMeterBinderProvider;
import org.springframework.boot.actuate.metrics.cache.RedisCacheMeterBinderProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.ehcache.EhCacheCache;
import org.springframework.cache.jcache.JCacheCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCache;

/**
 * Configure {@link CacheMeterBinderProvider} beans.
 *

 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(MeterBinder.class)
class CacheMeterBinderProvidersConfiguration {

	@Configuration(proxyBeanMethods = false)
	@ConditionalOnClass({ CaffeineCache.class, com.github.benmanes.caffeine.cache.Cache.class })
	static class CaffeineCacheMeterBinderProviderConfiguration {

		@Bean
		CaffeineCacheMeterBinderProvider caffeineCacheMeterBinderProvider() {
			return new CaffeineCacheMeterBinderProvider();
		}

	}

	@Configuration(proxyBeanMethods = false)
	@ConditionalOnClass({ EhCacheCache.class, Ehcache.class })
	static class EhCache2CacheMeterBinderProviderConfiguration {

		@Bean
		EhCache2CacheMeterBinderProvider ehCache2CacheMeterBinderProvider() {
			return new EhCache2CacheMeterBinderProvider();
		}

	}

	@Configuration(proxyBeanMethods = false)
	@ConditionalOnClass({ HazelcastCache.class, Hazelcast.class })
	static class HazelcastCacheMeterBinderProviderConfiguration {

		@Bean
		HazelcastCacheMeterBinderProvider hazelcastCacheMeterBinderProvider() {
			return new HazelcastCacheMeterBinderProvider();
		}

	}

	@Configuration(proxyBeanMethods = false)
	@ConditionalOnClass({ JCacheCache.class, javax.cache.CacheManager.class })
	static class JCacheCacheMeterBinderProviderConfiguration {

		@Bean
		JCacheCacheMeterBinderProvider jCacheCacheMeterBinderProvider() {
			return new JCacheCacheMeterBinderProvider();
		}

	}

	@Configuration(proxyBeanMethods = false)
	@ConditionalOnClass(RedisCache.class)
	static class RedisCacheMeterBinderProviderConfiguration {

		@Bean
		RedisCacheMeterBinderProvider redisCacheMeterBinderProvider() {
			return new RedisCacheMeterBinderProvider();
		}

	}

}
