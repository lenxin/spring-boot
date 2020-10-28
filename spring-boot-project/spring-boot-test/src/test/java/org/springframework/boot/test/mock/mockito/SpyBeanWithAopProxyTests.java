package org.springframework.boot.test.mock.mockito;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.cache.interceptor.SimpleCacheResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Test {@link SpyBean @SpyBean} when mixed with Spring AOP.
 *

 * @see <a href="https://github.com/spring-projects/spring-boot/issues/5837">5837</a>
 */
@ExtendWith(SpringExtension.class)
class SpyBeanWithAopProxyTests {

	@SpyBean
	private DateService dateService;

	@Test
	void verifyShouldUseProxyTarget() throws Exception {
		Long d1 = this.dateService.getDate(false);
		Thread.sleep(200);
		Long d2 = this.dateService.getDate(false);
		assertThat(d1).isEqualTo(d2);
		verify(this.dateService, times(1)).getDate(false);
		verify(this.dateService, times(1)).getDate(eq(false));
		verify(this.dateService, times(1)).getDate(anyBoolean());
	}

	@Configuration(proxyBeanMethods = false)
	@EnableCaching(proxyTargetClass = true)
	@Import(DateService.class)
	static class Config {

		@Bean
		CacheResolver cacheResolver(CacheManager cacheManager) {
			SimpleCacheResolver resolver = new SimpleCacheResolver();
			resolver.setCacheManager(cacheManager);
			return resolver;
		}

		@Bean
		ConcurrentMapCacheManager cacheManager() {
			ConcurrentMapCacheManager cacheManager = new ConcurrentMapCacheManager();
			cacheManager.setCacheNames(Arrays.asList("test"));
			return cacheManager;
		}

	}

	@Service
	public static class DateService {

		@Cacheable(cacheNames = "test")
		public Long getDate(boolean arg) {
			return System.nanoTime();
		}

	}

}
