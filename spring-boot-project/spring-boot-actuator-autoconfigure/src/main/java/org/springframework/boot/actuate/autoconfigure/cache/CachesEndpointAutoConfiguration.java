package org.springframework.boot.actuate.autoconfigure.cache;

import java.util.Map;

import org.springframework.boot.actuate.autoconfigure.endpoint.condition.ConditionalOnAvailableEndpoint;
import org.springframework.boot.actuate.cache.CachesEndpoint;
import org.springframework.boot.actuate.cache.CachesEndpointWebExtension;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for {@link CachesEndpoint}.
 *


 * @since 2.1.0
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(CacheManager.class)
@ConditionalOnAvailableEndpoint(endpoint = CachesEndpoint.class)
@AutoConfigureAfter(CacheAutoConfiguration.class)
public class CachesEndpointAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean
	public CachesEndpoint cachesEndpoint(Map<String, CacheManager> cacheManagers) {
		return new CachesEndpoint(cacheManagers);
	}

	@Bean
	@ConditionalOnMissingBean
	@ConditionalOnBean(CachesEndpoint.class)
	public CachesEndpointWebExtension cachesEndpointWebExtension(CachesEndpoint cachesEndpoint) {
		return new CachesEndpointWebExtension(cachesEndpoint);
	}

}
