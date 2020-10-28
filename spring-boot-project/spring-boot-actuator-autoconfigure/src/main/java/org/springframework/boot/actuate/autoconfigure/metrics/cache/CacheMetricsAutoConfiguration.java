package org.springframework.boot.actuate.autoconfigure.metrics.cache;

import org.springframework.boot.actuate.autoconfigure.metrics.MetricsAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for metrics on all available
 * {@link Cache caches}.
 *

 * @since 2.0.0
 */
@Configuration(proxyBeanMethods = false)
@AutoConfigureAfter({ MetricsAutoConfiguration.class, CacheAutoConfiguration.class })
@ConditionalOnBean(CacheManager.class)
@Import({ CacheMeterBinderProvidersConfiguration.class, CacheMetricsRegistrarConfiguration.class })
public class CacheMetricsAutoConfiguration {

}
