package org.springframework.boot.autoconfigure;

import java.util.Collections;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test for {@link AutoConfigurationMetadataLoader}.
 *

 */
class AutoConfigurationMetadataLoaderTests {

	@Test
	void loadShouldLoadProperties() {
		assertThat(load()).isNotNull();
	}

	@Test
	void wasProcessedWhenProcessedShouldReturnTrue() {
		assertThat(load().wasProcessed("test")).isTrue();
	}

	@Test
	void wasProcessedWhenNotProcessedShouldReturnFalse() {
		assertThat(load().wasProcessed("testx")).isFalse();
	}

	@Test
	void getIntegerShouldReturnValue() {
		assertThat(load().getInteger("test", "int")).isEqualTo(123);
	}

	@Test
	void getIntegerWhenMissingShouldReturnNull() {
		assertThat(load().getInteger("test", "intx")).isNull();
	}

	@Test
	void getIntegerWithDefaultWhenMissingShouldReturnDefault() {
		assertThat(load().getInteger("test", "intx", 345)).isEqualTo(345);
	}

	@Test
	void getSetShouldReturnValue() {
		assertThat(load().getSet("test", "set")).containsExactly("a", "b", "c");
	}

	@Test
	void getSetWhenMissingShouldReturnNull() {
		assertThat(load().getSet("test", "setx")).isNull();
	}

	@Test
	void getSetWithDefaultWhenMissingShouldReturnDefault() {
		assertThat(load().getSet("test", "setx", Collections.singleton("x"))).containsExactly("x");
	}

	@Test
	void getShouldReturnValue() {
		assertThat(load().get("test", "string")).isEqualTo("abc");
	}

	@Test
	void getWhenMissingShouldReturnNull() {
		assertThat(load().get("test", "stringx")).isNull();
	}

	@Test
	void getWithDefaultWhenMissingShouldReturnDefault() {
		assertThat(load().get("test", "stringx", "xyz")).isEqualTo("xyz");
	}

	private AutoConfigurationMetadata load() {
		return AutoConfigurationMetadataLoader.loadMetadata(null,
				"META-INF/AutoConfigurationMetadataLoaderTests.properties");
	}

}
