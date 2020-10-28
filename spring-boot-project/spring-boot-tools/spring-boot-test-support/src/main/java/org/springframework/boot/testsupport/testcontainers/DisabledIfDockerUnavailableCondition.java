package org.springframework.boot.testsupport.testcontainers;

import org.junit.jupiter.api.extension.ConditionEvaluationResult;
import org.junit.jupiter.api.extension.ExecutionCondition;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.testcontainers.DockerClientFactory;

/**
 * An {@link ExecutionCondition} that disables execution if Docker is unavailable.
 *


 */
class DisabledIfDockerUnavailableCondition implements ExecutionCondition {

	private static final String SILENCE_PROPERTY = "visibleassertions.silence";

	private static final ConditionEvaluationResult ENABLED = ConditionEvaluationResult.enabled("Docker available");

	private static final ConditionEvaluationResult DISABLED = ConditionEvaluationResult.disabled("Docker unavailable");

	@Override
	public ConditionEvaluationResult evaluateExecutionCondition(ExtensionContext context) {
		String originalSilenceValue = System.getProperty(SILENCE_PROPERTY);
		try {
			DockerClientFactory.instance().client();
			return ENABLED;
		}
		catch (Throwable ex) {
			return DISABLED;
		}
		finally {
			if (originalSilenceValue != null) {
				System.setProperty(SILENCE_PROPERTY, originalSilenceValue);
			}
			else {
				System.clearProperty(SILENCE_PROPERTY);
			}
		}
	}

}
