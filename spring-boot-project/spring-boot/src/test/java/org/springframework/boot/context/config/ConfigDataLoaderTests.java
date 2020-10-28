package org.springframework.boot.context.config;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

/**
 * Tests for {@link ConfigDataLoader}.
 *


 */
class ConfigDataLoaderTests {

	private TestConfigDataLoader loader = new TestConfigDataLoader();

	private ConfigDataLoaderContext context = mock(ConfigDataLoaderContext.class);

	@Test
	void isLoadableAlwaysReturnsTrue() {
		assertThat(this.loader.isLoadable(this.context, new TestConfigDataResource())).isTrue();
	}

	static class TestConfigDataLoader implements ConfigDataLoader<TestConfigDataResource> {

		@Override
		public ConfigData load(ConfigDataLoaderContext context, TestConfigDataResource resource) throws IOException {
			return null;
		}

	}

	static class TestConfigDataResource extends ConfigDataResource {

	}

}
