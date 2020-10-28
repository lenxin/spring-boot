package org.springframework.boot.docs.jpa;

import org.hibernate.cache.jcache.ConfigSettings;

import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.cache.jcache.JCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Example configuration of using JCache and Hibernate to enable second level caching.
 *

 */
// tag::configuration[]
@Configuration(proxyBeanMethods = false)
public class HibernateSecondLevelCacheExample {

	@Bean
	public HibernatePropertiesCustomizer hibernateSecondLevelCacheCustomizer(JCacheCacheManager cacheManager) {
		return (properties) -> properties.put(ConfigSettings.CACHE_MANAGER, cacheManager.getCacheManager());
	}

}
// end::configuration[]
