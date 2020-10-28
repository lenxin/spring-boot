package org.springframework.boot.web.reactive.context;

import org.junit.jupiter.api.Test;

import org.springframework.core.io.Resource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link GenericReactiveWebApplicationContext}
 *

 */
class GenericReactiveWebApplicationContextTests {

	@Test
	void getResourceByPath() throws Exception {
		GenericReactiveWebApplicationContext context = new GenericReactiveWebApplicationContext();
		Resource rootResource = context.getResourceByPath("/");
		assertThat(rootResource.exists()).isFalse();
		assertThat(rootResource.createRelative("application.properties").exists()).isFalse();
		context.close();
	}

}
