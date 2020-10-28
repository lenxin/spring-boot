package org.springframework.boot.actuate.autoconfigure.metrics.cache;

import io.micrometer.core.instrument.MeterRegistry;
import org.junit.jupiter.api.Test;

import org.springframework.boot.actuate.autoconfigure.metrics.test.MetricsRun;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link CacheMetricsAutoConfiguration}.
 *

 */
class CacheMetricsAutoConfigurationTests {

	private final ApplicationContextRunner contextRunner = new ApplicationContextRunner().with(MetricsRun.simple())
			.withUserConfiguration(CachingConfiguration.class).withConfiguration(
					AutoConfigurations.of(CacheAutoConfiguration.class, CacheMetricsAutoConfiguration.class));

	@Test
	void autoConfiguredCacheManagerIsInstrumented() {
		this.contextRunner.withPropertyValues("spring.cache.type=caffeine", "spring.cache.cache-names=cache1,cache2")
				.run((context) -> {
					MeterRegistry registry = context.getBean(MeterRegistry.class);
					registry.get("cache.gets").tags("name", "cache1").tags("cacheManager", "cacheManager").meter();
					registry.get("cache.gets").tags("name", "cache2").tags("cacheManager", "cacheManager").meter();
				});
	}

	@Test
	void autoConfiguredNonSupportedCacheManagerIsIgnored() {
		this.contextRunner.withPropertyValues("spring.cache.type=simple", "spring.cache.cache-names=cache1,cache2")
				.run((context) -> {
					MeterRegistry registry = context.getBean(MeterRegistry.class);
					assertThat(registry.find("cache.gets").tags("name", "cache1").tags("cacheManager", "cacheManager")
							.meter()).isNull();
					assertThat(registry.find("cache.gets").tags("name", "cache2").tags("cacheManager", "cacheManager")
							.meter()).isNull();
				});
	}

	@Test
	void cacheInstrumentationCanBeDisabled() {
		this.contextRunner.withPropertyValues("management.metrics.enable.cache=false", "spring.cache.type=caffeine",
				"spring.cache.cache-names=cache1").run((context) -> {
					MeterRegistry registry = context.getBean(MeterRegistry.class);
					assertThat(registry.find("cache.requests").tags("name", "cache1")
							.tags("cacheManager", "cacheManager").meter()).isNull();
				});
	}

	@Configuration(proxyBeanMethods = false)
	@EnableCaching
	static class CachingConfiguration {

	}

}
