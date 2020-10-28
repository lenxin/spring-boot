package org.springframework.boot.context.properties.source;

import org.junit.jupiter.api.Test;

import org.springframework.mock.env.MockPropertySource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

/**
 * Tests for {@link ConfigurationPropertySource}.
 *

 */
class ConfigurationPropertySourceTests {

	@Test
	void fromCreatesConfigurationPropertySourcesPropertySource() {
		MockPropertySource source = new MockPropertySource();
		source.setProperty("spring", "boot");
		ConfigurationPropertySource adapted = ConfigurationPropertySource.from(source);
		assertThat(adapted.getConfigurationProperty(ConfigurationPropertyName.of("spring")).getValue())
				.isEqualTo("boot");
	}

	@Test
	void fromWhenSourceIsAlreadyConfigurationPropertySourcesPropertySourceReturnsNull() {
		ConfigurationPropertySourcesPropertySource source = mock(ConfigurationPropertySourcesPropertySource.class);
		assertThat(ConfigurationPropertySource.from(source)).isNull();
	}

}
