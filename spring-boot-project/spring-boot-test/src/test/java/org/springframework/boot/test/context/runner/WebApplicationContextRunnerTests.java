package org.springframework.boot.test.context.runner;

import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.assertj.AssertableWebApplicationContext;
import org.springframework.mock.web.MockServletContext;
import org.springframework.web.context.ConfigurableWebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link WebApplicationContextRunner}.
 *


 */
class WebApplicationContextRunnerTests extends
		AbstractApplicationContextRunnerTests<WebApplicationContextRunner, ConfigurableWebApplicationContext, AssertableWebApplicationContext> {

	@Test
	void contextShouldHaveMockServletContext() {
		get().run((context) -> assertThat(context.getServletContext()).isInstanceOf(MockServletContext.class));
	}

	@Override
	protected WebApplicationContextRunner get() {
		return new WebApplicationContextRunner();
	}

}
