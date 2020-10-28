package org.springframework.boot.test.context.assertj;

import org.junit.jupiter.api.Test;

import org.springframework.context.ConfigurableApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

/**
 * Tests for {@link AssertableApplicationContext}.
 *

 * @see ApplicationContextAssertProviderTests
 */
class AssertableApplicationContextTests {

	@Test
	void getShouldReturnProxy() {
		AssertableApplicationContext context = AssertableApplicationContext
				.get(() -> mock(ConfigurableApplicationContext.class));
		assertThat(context).isInstanceOf(ConfigurableApplicationContext.class);
	}

}
