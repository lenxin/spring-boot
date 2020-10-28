package org.springframework.boot.docs.cache;

import java.time.Duration;

import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;

/**
 * An example how to customize {@code RedisCacheManagerBuilder} via
 * {@code RedisCacheManagerBuilderCustomizer}.
 *

 */
@Configuration(proxyBeanMethods = false)
public class RedisCacheManagerCustomizationExample {

	// tag::configuration[]
	@Bean
	public RedisCacheManagerBuilderCustomizer myRedisCacheManagerBuilderCustomizer() {
		return (builder) -> builder
				.withCacheConfiguration("cache1",
						RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofSeconds(10)))
				.withCacheConfiguration("cache2",
						RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(1)));

	}
	// end::configuration[]

}
