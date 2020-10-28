package org.springframework.boot.autoconfigure.cache;

import java.util.LinkedHashSet;
import java.util.List;

import com.couchbase.client.java.Cluster;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.cache.CacheProperties.Couchbase;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.couchbase.CouchbaseClientFactory;
import org.springframework.data.couchbase.cache.CouchbaseCacheManager;
import org.springframework.data.couchbase.cache.CouchbaseCacheManager.CouchbaseCacheManagerBuilder;
import org.springframework.util.ObjectUtils;

/**
 * Couchbase cache configuration.
 *

 * @since 1.4.0
 * @deprecated since 2.3.3 as this class is not intended for public use. It will be made
 * package-private in a future release
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass({ Cluster.class, CouchbaseClientFactory.class, CouchbaseCacheManager.class })
@ConditionalOnMissingBean(CacheManager.class)
@ConditionalOnSingleCandidate(CouchbaseClientFactory.class)
@Conditional(CacheCondition.class)
@Deprecated
public class CouchbaseCacheConfiguration {

	@Bean
	public CouchbaseCacheManager cacheManager(CacheProperties cacheProperties, CacheManagerCustomizers customizers,
			ObjectProvider<CouchbaseCacheManagerBuilderCustomizer> couchbaseCacheManagerBuilderCustomizers,
			CouchbaseClientFactory clientFactory) {
		List<String> cacheNames = cacheProperties.getCacheNames();
		CouchbaseCacheManagerBuilder builder = CouchbaseCacheManager.builder(clientFactory);
		Couchbase couchbase = cacheProperties.getCouchbase();
		org.springframework.data.couchbase.cache.CouchbaseCacheConfiguration config = org.springframework.data.couchbase.cache.CouchbaseCacheConfiguration
				.defaultCacheConfig();
		if (couchbase.getExpiration() != null) {
			config = config.entryExpiry(couchbase.getExpiration());
		}
		builder.cacheDefaults(config);
		if (!ObjectUtils.isEmpty(cacheNames)) {
			builder.initialCacheNames(new LinkedHashSet<>(cacheNames));
		}
		couchbaseCacheManagerBuilderCustomizers.orderedStream().forEach((customizer) -> customizer.customize(builder));
		CouchbaseCacheManager cacheManager = builder.build();
		return customizers.customize(cacheManager);
	}

}
