package org.springframework.boot.autoconfigure.security.reactive;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link PathRequest}.
 *

 */
class PathRequestTests {

	@Test
	void toStaticResourcesShouldReturnStaticResourceRequest() {
		assertThat(PathRequest.toStaticResources()).isInstanceOf(StaticResourceRequest.class);
	}

}
