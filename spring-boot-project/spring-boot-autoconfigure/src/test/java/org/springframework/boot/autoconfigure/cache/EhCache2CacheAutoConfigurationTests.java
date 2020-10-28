package org.springframework.boot.autoconfigure.cache;

import org.junit.jupiter.api.Test;

import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.autoconfigure.cache.CacheAutoConfigurationTests.DefaultCacheAndCustomizersConfiguration;
import org.springframework.boot.autoconfigure.cache.CacheAutoConfigurationTests.DefaultCacheConfiguration;
import org.springframework.boot.autoconfigure.cache.CacheAutoConfigurationTests.EhCacheCustomCacheManager;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.boot.testsupport.classpath.ClassPathExclusions;
import org.springframework.cache.ehcache.EhCacheCacheManager;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link CacheAutoConfiguration} with EhCache 2.
 *


 */
@ClassPathExclusions("ehcache-3*.jar")
class EhCache2CacheAutoConfigurationTests extends AbstractCacheAutoConfigurationTests {

	private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
			.withConfiguration(AutoConfigurations.of(CacheAutoConfiguration.class));

	@Test
	void ehCacheWithCaches() {
		this.contextRunner.withUserConfiguration(DefaultCacheConfiguration.class)
				.withPropertyValues("spring.cache.type=ehcache").run((context) -> {
					EhCacheCacheManager cacheManager = getCacheManager(context, EhCacheCacheManager.class);
					assertThat(cacheManager.getCacheNames()).containsOnly("cacheTest1", "cacheTest2");
					assertThat(context.getBean(net.sf.ehcache.CacheManager.class))
							.isEqualTo(cacheManager.getCacheManager());
				});
	}

	@Test
	void ehCacheWithCustomizers() {
		this.contextRunner.withUserConfiguration(DefaultCacheAndCustomizersConfiguration.class)
				.withPropertyValues("spring.cache.type=ehcache")
				.run(verifyCustomizers("allCacheManagerCustomizer", "ehcacheCacheManagerCustomizer"));
	}

	@Test
	void ehCacheWithConfig() {
		this.contextRunner.withUserConfiguration(DefaultCacheConfiguration.class)
				.withPropertyValues("spring.cache.type=ehcache",
						"spring.cache.ehcache.config=cache/ehcache-override.xml")
				.run((context) -> {
					EhCacheCacheManager cacheManager = getCacheManager(context, EhCacheCacheManager.class);
					assertThat(cacheManager.getCacheNames()).containsOnly("cacheOverrideTest1", "cacheOverrideTest2");
				});
	}

	@Test
	void ehCacheWithExistingCacheManager() {
		this.contextRunner.withUserConfiguration(EhCacheCustomCacheManager.class)
				.withPropertyValues("spring.cache.type=ehcache").run((context) -> {
					EhCacheCacheManager cacheManager = getCacheManager(context, EhCacheCacheManager.class);
					assertThat(cacheManager.getCacheManager()).isEqualTo(context.getBean("customEhCacheCacheManager"));
				});
	}

}
