package org.springframework.boot.test.context.assertj;

import org.junit.jupiter.api.Test;

import org.springframework.boot.web.reactive.context.ConfigurableReactiveWebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

/**
 * Tests for {@link AssertableReactiveWebApplicationContext}.
 *

 * @see ApplicationContextAssertProviderTests
 */
class AssertableReactiveWebApplicationContextTests {

	@Test
	void getShouldReturnProxy() {
		AssertableReactiveWebApplicationContext context = AssertableReactiveWebApplicationContext
				.get(() -> mock(ConfigurableReactiveWebApplicationContext.class));
		assertThat(context).isInstanceOf(ConfigurableReactiveWebApplicationContext.class);
	}

}
