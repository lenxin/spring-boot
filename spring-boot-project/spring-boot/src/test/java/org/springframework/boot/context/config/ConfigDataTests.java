package org.springframework.boot.context.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import org.springframework.boot.context.config.ConfigData.Option;
import org.springframework.core.env.MapPropertySource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/**
 * Tests for {@link ConfigData}.
 *


 */
class ConfigDataTests {

	@Test
	void createWhenPropertySourcesIsNullThrowsException() {
		assertThatIllegalArgumentException().isThrownBy(() -> new ConfigData(null))
				.withMessage("PropertySources must not be null");
	}

	@Test
	void createWhenOptionsIsNullThrowsException() {
		assertThatIllegalArgumentException().isThrownBy(() -> new ConfigData(Collections.emptyList(), (Option[]) null))
				.withMessage("Options must not be null");
	}

	@Test
	void getPropertySourcesReturnsCopyOfSources() {
		MapPropertySource source = new MapPropertySource("test", Collections.emptyMap());
		List<MapPropertySource> sources = new ArrayList<>(Collections.singleton(source));
		ConfigData configData = new ConfigData(sources);
		sources.clear();
		assertThat(configData.getPropertySources()).containsExactly(source);
	}

	@Test
	void getOptionsReturnsCopyOfOptions() {
		MapPropertySource source = new MapPropertySource("test", Collections.emptyMap());
		Option[] options = { Option.IGNORE_IMPORTS };
		ConfigData configData = new ConfigData(Collections.singleton(source), options);
		options[0] = null;
		assertThat(configData.getOptions()).containsExactly(Option.IGNORE_IMPORTS);
	}

}
