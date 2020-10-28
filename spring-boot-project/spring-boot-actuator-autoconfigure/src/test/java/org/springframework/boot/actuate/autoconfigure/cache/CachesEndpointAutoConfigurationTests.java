package org.springframework.boot.actuate.autoconfigure.cache;

import org.junit.jupiter.api.Test;

import org.springframework.boot.actuate.cache.CachesEndpoint;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.cache.CacheManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

/**
 * Tests for {@link CachesEndpointAutoConfiguration}.
 *


 */
class CachesEndpointAutoConfigurationTests {

	private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
			.withConfiguration(AutoConfigurations.of(CachesEndpointAutoConfiguration.class));

	@Test
	void runShouldHaveEndpointBean() {
		this.contextRunner.withBean(CacheManager.class, () -> mock(CacheManager.class))
				.withPropertyValues("management.endpoints.web.exposure.include=caches")
				.run((context) -> assertThat(context).hasSingleBean(CachesEndpoint.class));
	}

	@Test
	void runWithoutCacheManagerShouldHaveEndpointBean() {
		this.contextRunner.withPropertyValues("management.endpoints.web.exposure.include=caches")
				.run((context) -> assertThat(context).hasSingleBean(CachesEndpoint.class));
	}

	@Test
	void runWhenNotExposedShouldNotHaveEndpointBean() {
		this.contextRunner.withBean(CacheManager.class, () -> mock(CacheManager.class))
				.run((context) -> assertThat(context).doesNotHaveBean(CachesEndpoint.class));
	}

	@Test
	void runWhenEnabledPropertyIsFalseShouldNotHaveEndpointBean() {
		this.contextRunner.withPropertyValues("management.endpoint.caches.enabled:false")
				.withPropertyValues("management.endpoints.web.exposure.include=*")
				.withBean(CacheManager.class, () -> mock(CacheManager.class))
				.run((context) -> assertThat(context).doesNotHaveBean(CachesEndpoint.class));
	}

}
