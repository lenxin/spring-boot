package org.springframework.boot.test.autoconfigure.web.servlet;

import org.springframework.core.Ordered;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.TestExecutionListener;
import org.springframework.test.context.support.AbstractTestExecutionListener;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

/**
 * {@link TestExecutionListener} to reset the {@link WebDriverScope}.
 *

 * @see WebDriverContextCustomizerFactory
 * @see WebDriverScope
 */
class WebDriverTestExecutionListener extends AbstractTestExecutionListener {

	@Override
	public int getOrder() {
		return Ordered.LOWEST_PRECEDENCE - 100;
	}

	@Override
	public void afterTestMethod(TestContext testContext) throws Exception {
		WebDriverScope scope = WebDriverScope.getFrom(testContext.getApplicationContext());
		if (scope != null && scope.reset()) {
			testContext.setAttribute(DependencyInjectionTestExecutionListener.REINJECT_DEPENDENCIES_ATTRIBUTE,
					Boolean.TRUE);
		}
	}

}
