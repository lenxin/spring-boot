package org.springframework.boot.test.autoconfigure.restdocs;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.core.Ordered;
import org.springframework.restdocs.ManualRestDocumentation;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.TestExecutionListener;
import org.springframework.test.context.support.AbstractTestExecutionListener;
import org.springframework.util.ClassUtils;

/**
 * A {@link TestExecutionListener} for Spring REST Docs that removes the need for a
 * {@code @Rule} when using JUnit or manual before and after test calls when using TestNG.
 *

 * @since 1.4.0
 */
public class RestDocsTestExecutionListener extends AbstractTestExecutionListener {

	private static final String REST_DOCS_CLASS = "org.springframework.restdocs.ManualRestDocumentation";

	@Override
	public int getOrder() {
		return Ordered.LOWEST_PRECEDENCE - 100;
	}

	@Override
	public void beforeTestMethod(TestContext testContext) throws Exception {
		if (restDocsIsPresent()) {
			new DocumentationHandler().beforeTestMethod(testContext);
		}
	}

	@Override
	public void afterTestMethod(TestContext testContext) throws Exception {
		if (restDocsIsPresent()) {
			new DocumentationHandler().afterTestMethod(testContext);
		}
	}

	private boolean restDocsIsPresent() {
		return ClassUtils.isPresent(REST_DOCS_CLASS, getClass().getClassLoader());
	}

	private static class DocumentationHandler {

		private void beforeTestMethod(TestContext testContext) throws Exception {
			ManualRestDocumentation restDocumentation = findManualRestDocumentation(testContext);
			if (restDocumentation != null) {
				restDocumentation.beforeTest(testContext.getTestClass(), testContext.getTestMethod().getName());
			}
		}

		private void afterTestMethod(TestContext testContext) {
			ManualRestDocumentation restDocumentation = findManualRestDocumentation(testContext);
			if (restDocumentation != null) {
				restDocumentation.afterTest();
			}
		}

		private ManualRestDocumentation findManualRestDocumentation(TestContext testContext) {
			try {
				return testContext.getApplicationContext().getBean(ManualRestDocumentation.class);
			}
			catch (NoSuchBeanDefinitionException ex) {
				return null;
			}
		}

	}

}
