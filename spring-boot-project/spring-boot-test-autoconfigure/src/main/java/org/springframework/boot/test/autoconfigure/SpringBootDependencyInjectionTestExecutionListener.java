package org.springframework.boot.test.autoconfigure;

import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.boot.autoconfigure.condition.ConditionEvaluationReport;
import org.springframework.boot.autoconfigure.logging.ConditionEvaluationReportMessage;
import org.springframework.boot.test.context.DefaultTestExecutionListenersPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.TestExecutionListener;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

/**
 * Alternative {@link DependencyInjectionTestExecutionListener} prints the
 * {@link ConditionEvaluationReport} when the context cannot be prepared.
 *

 * @since 1.4.1
 */
public class SpringBootDependencyInjectionTestExecutionListener extends DependencyInjectionTestExecutionListener {

	@Override
	public void prepareTestInstance(TestContext testContext) throws Exception {
		try {
			super.prepareTestInstance(testContext);
		}
		catch (Exception ex) {
			outputConditionEvaluationReport(testContext);
			throw ex;
		}
	}

	private void outputConditionEvaluationReport(TestContext testContext) {
		try {
			ApplicationContext context = testContext.getApplicationContext();
			if (context instanceof ConfigurableApplicationContext) {
				ConditionEvaluationReport report = ConditionEvaluationReport
						.get(((ConfigurableApplicationContext) context).getBeanFactory());
				System.err.println(new ConditionEvaluationReportMessage(report));
			}
		}
		catch (Exception ex) {
			// Allow original failure to be reported
		}
	}

	static class PostProcessor implements DefaultTestExecutionListenersPostProcessor {

		@Override
		public Set<Class<? extends TestExecutionListener>> postProcessDefaultTestExecutionListeners(
				Set<Class<? extends TestExecutionListener>> listeners) {
			Set<Class<? extends TestExecutionListener>> updated = new LinkedHashSet<>(listeners.size());
			for (Class<? extends TestExecutionListener> listener : listeners) {
				updated.add(listener.equals(DependencyInjectionTestExecutionListener.class)
						? SpringBootDependencyInjectionTestExecutionListener.class : listener);
			}
			return updated;
		}

	}

}
