package org.springframework.boot.context.config;

import java.util.List;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

/**
 * Tests for {@link ConfigDataLocationResolver}.
 *


 */
class ConfigDataLocationResolverTests {

	private ConfigDataLocationResolver<?> resolver = new TestConfigDataLocationResolver();

	private ConfigDataLocationResolverContext context = mock(ConfigDataLocationResolverContext.class);

	@Test
	void resolveProfileSpecificReturnsEmptyList() {
		assertThat(this.resolver.resolveProfileSpecific(this.context, null, null)).isEmpty();
	}

	static class TestConfigDataLocationResolver implements ConfigDataLocationResolver<ConfigDataResource> {

		@Override
		public boolean isResolvable(ConfigDataLocationResolverContext context, ConfigDataLocation location) {
			return true;
		}

		@Override
		public List<ConfigDataResource> resolve(ConfigDataLocationResolverContext context,
				ConfigDataLocation location) {
			return null;
		}

	}

}
