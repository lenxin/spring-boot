package org.springframework.boot.actuate.endpoint.web;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link EndpointMapping}.
 *

 */
class EndpointMappingTests {

	@Test
	void normalizationTurnsASlashIntoAnEmptyString() {
		assertThat(new EndpointMapping("/").getPath()).isEqualTo("");
	}

	@Test
	void normalizationLeavesAnEmptyStringAsIs() {
		assertThat(new EndpointMapping("").getPath()).isEqualTo("");
	}

	@Test
	void normalizationRemovesATrailingSlash() {
		assertThat(new EndpointMapping("/test/").getPath()).isEqualTo("/test");
	}

	@Test
	void normalizationAddsALeadingSlash() {
		assertThat(new EndpointMapping("test").getPath()).isEqualTo("/test");
	}

	@Test
	void normalizationAddsALeadingSlashAndRemovesATrailingSlash() {
		assertThat(new EndpointMapping("test/").getPath()).isEqualTo("/test");
	}

	@Test
	void normalizationLeavesAPathWithALeadingSlashAndNoTrailingSlashAsIs() {
		assertThat(new EndpointMapping("/test").getPath()).isEqualTo("/test");
	}

	@Test
	void subPathForAnEmptyStringReturnsBasePath() {
		assertThat(new EndpointMapping("/test").createSubPath("")).isEqualTo("/test");
	}

	@Test
	void subPathWithALeadingSlashIsSeparatedFromBasePathBySingleSlash() {
		assertThat(new EndpointMapping("/test").createSubPath("/one")).isEqualTo("/test/one");
	}

	@Test
	void subPathWithoutALeadingSlashIsSeparatedFromBasePathBySingleSlash() {
		assertThat(new EndpointMapping("/test").createSubPath("one")).isEqualTo("/test/one");
	}

	@Test
	void trailingSlashIsRemovedFromASubPath() {
		assertThat(new EndpointMapping("/test").createSubPath("one/")).isEqualTo("/test/one");
	}

}
