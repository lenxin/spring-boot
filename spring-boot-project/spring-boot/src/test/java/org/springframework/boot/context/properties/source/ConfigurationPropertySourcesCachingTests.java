package org.springframework.boot.context.properties.source;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.withSettings;

/**
 * Tests for {@link ConfigurationPropertySourcesCaching}.
 *

 */
class ConfigurationPropertySourcesCachingTests {

	private List<ConfigurationPropertySource> sources;

	private ConfigurationPropertySourcesCaching caching;

	@BeforeEach
	void setup() {
		this.sources = new ArrayList<>();
		for (int i = 0; i < 4; i++) {
			this.sources.add(mockSource(i % 2 == 0));
		}
		this.caching = new ConfigurationPropertySourcesCaching(this.sources);
	}

	private ConfigurationPropertySource mockSource(boolean cachingSource) {
		if (!cachingSource) {
			return mock(ConfigurationPropertySource.class);
		}
		ConfigurationPropertySource source = mock(ConfigurationPropertySource.class,
				withSettings().extraInterfaces(CachingConfigurationPropertySource.class));
		ConfigurationPropertyCaching caching = mock(ConfigurationPropertyCaching.class);
		given(((CachingConfigurationPropertySource) source).getCaching()).willReturn(caching);
		return source;
	}

	@Test
	void enableDelegatesToCachingConfigurationPropertySources() {
		this.caching.enable();
		verify(getCaching(0)).enable();
		verify(getCaching(2)).enable();
	}

	@Test
	void enableWhenSourcesIsNullDoesNothing() {
		new ConfigurationPropertySourcesCaching(null).enable();
	}

	@Test
	void disableDelegatesToCachingConfigurationPropertySources() {
		this.caching.disable();
		verify(getCaching(0)).disable();
		verify(getCaching(2)).disable();
	}

	@Test
	void disableWhenSourcesIsNullDoesNothing() {
		new ConfigurationPropertySourcesCaching(null).disable();
	}

	@Test
	void setTimeToLiveDelegatesToCachingConfigurationPropertySources() {
		Duration ttl = Duration.ofDays(1);
		this.caching.setTimeToLive(ttl);
		verify(getCaching(0)).setTimeToLive(ttl);
		verify(getCaching(2)).setTimeToLive(ttl);
	}

	@Test
	void setTimeToLiveWhenSourcesIsNullDoesNothing() {
		new ConfigurationPropertySourcesCaching(null).setTimeToLive(Duration.ofSeconds(1));
	}

	@Test
	void clearDelegatesToCachingConfigurationPropertySources() {
		this.caching.clear();
		verify(getCaching(0)).clear();
		verify(getCaching(2)).clear();
	}

	@Test
	void clearWhenSourcesIsNullDoesNothing() {
		new ConfigurationPropertySourcesCaching(null).enable();
	}

	private ConfigurationPropertyCaching getCaching(int index) {
		return CachingConfigurationPropertySource.find(this.sources.get(index));
	}

}
