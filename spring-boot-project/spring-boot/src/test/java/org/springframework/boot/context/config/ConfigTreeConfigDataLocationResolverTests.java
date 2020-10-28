package org.springframework.boot.context.config;

import java.io.File;
import java.util.List;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

/**
 * Tests for {@link ConfigTreeConfigDataLocationResolver}.
 *


 */
class ConfigTreeConfigDataLocationResolverTests {

	private ConfigTreeConfigDataLocationResolver resolver = new ConfigTreeConfigDataLocationResolver();

	private ConfigDataLocationResolverContext context = mock(ConfigDataLocationResolverContext.class);

	@Test
	void isResolvableWhenPrefixMatchesReturnsTrue() {
		assertThat(this.resolver.isResolvable(this.context, ConfigDataLocation.of("configtree:/etc/config"))).isTrue();
	}

	@Test
	void isResolvableWhenPrefixDoesNotMatchReturnsFalse() {
		assertThat(this.resolver.isResolvable(this.context, ConfigDataLocation.of("http://etc/config"))).isFalse();
		assertThat(this.resolver.isResolvable(this.context, ConfigDataLocation.of("/etc/config"))).isFalse();
	}

	@Test
	void resolveReturnsConfigVolumeMountLocation() {
		List<ConfigTreeConfigDataResource> locations = this.resolver.resolve(this.context,
				ConfigDataLocation.of("configtree:/etc/config"));
		assertThat(locations.size()).isEqualTo(1);
		assertThat(locations).extracting(Object::toString)
				.containsExactly("config tree [" + new File("/etc/config").getAbsolutePath() + "]");
	}

}
