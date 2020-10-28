package org.springframework.boot.autoconfigure.cache;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cache.CacheManager;
import org.springframework.cache.support.NoOpCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

/**
 * No-op cache configuration used to disable caching via configuration.
 *

 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnMissingBean(CacheManager.class)
@Conditional(CacheCondition.class)
class NoOpCacheConfiguration {

	@Bean
	NoOpCacheManager cacheManager() {
		return new NoOpCacheManager();
	}

}
