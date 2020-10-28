package org.springframework.boot.test.context.runner;

import org.springframework.boot.test.context.assertj.AssertableReactiveWebApplicationContext;
import org.springframework.boot.web.reactive.context.ConfigurableReactiveWebApplicationContext;

/**
 * Tests for {@link ReactiveWebApplicationContextRunner}.
 *


 */
public class ReactiveWebApplicationContextRunnerTests extends
		AbstractApplicationContextRunnerTests<ReactiveWebApplicationContextRunner, ConfigurableReactiveWebApplicationContext, AssertableReactiveWebApplicationContext> {

	@Override
	protected ReactiveWebApplicationContextRunner get() {
		return new ReactiveWebApplicationContextRunner();
	}

}
