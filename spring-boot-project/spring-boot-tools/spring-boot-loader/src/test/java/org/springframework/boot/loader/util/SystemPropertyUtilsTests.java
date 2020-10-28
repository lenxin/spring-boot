package org.springframework.boot.loader.util;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link SystemPropertyUtils}.
 *

 */
class SystemPropertyUtilsTests {

	@BeforeEach
	void init() {
		System.setProperty("foo", "bar");
	}

	@AfterEach
	void close() {
		System.clearProperty("foo");
	}

	@Test
	void testVanillaPlaceholder() {
		assertThat(SystemPropertyUtils.resolvePlaceholders("${foo}")).isEqualTo("bar");
	}

	@Test
	void testDefaultValue() {
		assertThat(SystemPropertyUtils.resolvePlaceholders("${bar:foo}")).isEqualTo("foo");
	}

	@Test
	void testNestedPlaceholder() {
		assertThat(SystemPropertyUtils.resolvePlaceholders("${bar:${spam:foo}}")).isEqualTo("foo");
	}

	@Test
	void testEnvVar() {
		assertThat(SystemPropertyUtils.getProperty("lang")).isEqualTo(System.getenv("LANG"));
	}

}
