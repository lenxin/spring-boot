package org.springframework.boot.maven;

import org.junit.jupiter.api.Test;

import org.springframework.boot.maven.AbstractRunMojo.SystemPropertyFormatter;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link AbstractRunMojo.SystemPropertyFormatter}.
 */
class SystemPropertyFormatterTests {

	@Test
	void parseEmpty() {
		assertThat(SystemPropertyFormatter.format(null, null)).isEqualTo("");
	}

	@Test
	void parseOnlyKey() {
		assertThat(SystemPropertyFormatter.format("key1", null)).isEqualTo("-Dkey1");
	}

	@Test
	void parseKeyWithValue() {
		assertThat(SystemPropertyFormatter.format("key1", "value1")).isEqualTo("-Dkey1=\"value1\"");
	}

	@Test
	void parseKeyWithEmptyValue() {
		assertThat(SystemPropertyFormatter.format("key1", "")).isEqualTo("-Dkey1");
	}

	@Test
	void parseKeyWithOnlySpaces() {
		assertThat(SystemPropertyFormatter.format("key1", "   ")).isEqualTo("-Dkey1=\"   \"");
	}

}
