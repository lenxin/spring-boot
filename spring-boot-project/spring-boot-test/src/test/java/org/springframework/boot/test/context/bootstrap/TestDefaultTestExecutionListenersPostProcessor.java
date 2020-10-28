package org.springframework.boot.test.context.bootstrap;

import java.util.Set;

import org.springframework.boot.test.context.DefaultTestExecutionListenersPostProcessor;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.TestExecutionListener;
import org.springframework.test.context.support.AbstractTestExecutionListener;

/**
 * Test {@link DefaultTestExecutionListenersPostProcessor}.
 *

 */
public class TestDefaultTestExecutionListenersPostProcessor implements DefaultTestExecutionListenersPostProcessor {

	@Override
	public Set<Class<? extends TestExecutionListener>> postProcessDefaultTestExecutionListeners(
			Set<Class<? extends TestExecutionListener>> listeners) {
		listeners.add(ExampleTestExecutionListener.class);
		return listeners;
	}

	static class ExampleTestExecutionListener extends AbstractTestExecutionListener {

		@Override
		public void prepareTestInstance(TestContext testContext) throws Exception {
			Object testInstance = testContext.getTestInstance();
			if (testInstance instanceof SpringBootTestContextBootstrapperIntegrationTests) {
				SpringBootTestContextBootstrapperIntegrationTests test = (SpringBootTestContextBootstrapperIntegrationTests) testInstance;
				test.defaultTestExecutionListenersPostProcessorCalled = true;
			}
		}

	}

}
