package org.springframework.boot.test.autoconfigure.core;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.support.NoOpCacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link AutoConfigureCache @AutoConfigureCache}.
 *

 */
@SpringBootTest
@AutoConfigureCache
class AutoConfigureCacheIntegrationTests {

	@Autowired
	private ApplicationContext applicationContext;

	@Test
	void shouldConfigureNoOpCacheManager() {
		CacheManager bean = this.applicationContext.getBean(CacheManager.class);
		assertThat(bean).isInstanceOf(NoOpCacheManager.class);
	}

	@Configuration(proxyBeanMethods = false)
	@EnableCaching
	static class Config {

	}

}
