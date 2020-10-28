package org.springframework.boot.test.context.runner;

import org.springframework.boot.test.context.assertj.AssertableApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Tests for {@link ApplicationContextRunner}.
 *


 */
public class ApplicationContextRunnerTests extends
		AbstractApplicationContextRunnerTests<ApplicationContextRunner, ConfigurableApplicationContext, AssertableApplicationContext> {

	@Override
	protected ApplicationContextRunner get() {
		return new ApplicationContextRunner();
	}

}
