package org.springframework.boot.autoconfigure.web.servlet;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link DispatcherServletPath}.
 *

 */
class DispatcherServletPathTests {

	@Test
	void getRelativePathReturnsRelativePath() {
		assertThat(((DispatcherServletPath) () -> "spring").getRelativePath("boot")).isEqualTo("spring/boot");
		assertThat(((DispatcherServletPath) () -> "spring/").getRelativePath("boot")).isEqualTo("spring/boot");
		assertThat(((DispatcherServletPath) () -> "spring").getRelativePath("/boot")).isEqualTo("spring/boot");
	}

	@Test
	void getPrefixWhenHasSimplePathReturnPath() {
		assertThat(((DispatcherServletPath) () -> "spring").getPrefix()).isEqualTo("spring");
	}

	@Test
	void getPrefixWhenHasPatternRemovesPattern() {
		assertThat(((DispatcherServletPath) () -> "spring/*.do").getPrefix()).isEqualTo("spring");
	}

	@Test
	void getPathWhenPathEndsWithSlashRemovesSlash() {
		assertThat(((DispatcherServletPath) () -> "spring/").getPrefix()).isEqualTo("spring");
	}

	@Test
	void getServletUrlMappingWhenPathIsEmptyReturnsSlash() {
		assertThat(((DispatcherServletPath) () -> "").getServletUrlMapping()).isEqualTo("/");
	}

	@Test
	void getServletUrlMappingWhenPathIsSlashReturnsSlash() {
		assertThat(((DispatcherServletPath) () -> "/").getServletUrlMapping()).isEqualTo("/");
	}

	@Test
	void getServletUrlMappingWhenPathContainsStarReturnsPath() {
		assertThat(((DispatcherServletPath) () -> "spring/*.do").getServletUrlMapping()).isEqualTo("spring/*.do");
	}

	@Test
	void getServletUrlMappingWhenHasPathNotEndingSlashReturnsSlashStarPattern() {
		assertThat(((DispatcherServletPath) () -> "spring/boot").getServletUrlMapping()).isEqualTo("spring/boot/*");
	}

	@Test
	void getServletUrlMappingWhenHasPathEndingWithSlashReturnsSlashStarPattern() {
		assertThat(((DispatcherServletPath) () -> "spring/boot/").getServletUrlMapping()).isEqualTo("spring/boot/*");
	}

}
