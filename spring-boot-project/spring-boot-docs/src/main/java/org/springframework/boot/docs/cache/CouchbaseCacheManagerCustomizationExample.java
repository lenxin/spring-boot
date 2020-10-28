package org.springframework.boot.docs.cache;

import java.time.Duration;

import org.springframework.boot.autoconfigure.cache.CouchbaseCacheManagerBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.couchbase.cache.CouchbaseCacheConfiguration;

/**
 * An example how to customize {@code CouchbaseCacheManagerBuilder} via
 * {@code CouchbaseCacheManagerBuilderCustomizer}.
 *

 */
@Configuration(proxyBeanMethods = false)
public class CouchbaseCacheManagerCustomizationExample {

	// tag::configuration[]
	@Bean
	public CouchbaseCacheManagerBuilderCustomizer myCouchbaseCacheManagerBuilderCustomizer() {
		return (builder) -> builder
				.withCacheConfiguration("cache1",
						CouchbaseCacheConfiguration.defaultCacheConfig().entryExpiry(Duration.ofSeconds(10)))
				.withCacheConfiguration("cache2",
						CouchbaseCacheConfiguration.defaultCacheConfig().entryExpiry(Duration.ofMinutes(1)));

	}
	// end::configuration[]

}
