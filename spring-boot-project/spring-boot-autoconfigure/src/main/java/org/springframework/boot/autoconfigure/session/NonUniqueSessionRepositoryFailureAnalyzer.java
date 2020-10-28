package org.springframework.boot.autoconfigure.session;

import org.springframework.boot.diagnostics.AbstractFailureAnalyzer;
import org.springframework.boot.diagnostics.FailureAnalysis;

/**
 * An {@link AbstractFailureAnalyzer} for {@link NonUniqueSessionRepositoryException}.
 *

 */
class NonUniqueSessionRepositoryFailureAnalyzer extends AbstractFailureAnalyzer<NonUniqueSessionRepositoryException> {

	@Override
	protected FailureAnalysis analyze(Throwable rootFailure, NonUniqueSessionRepositoryException cause) {
		StringBuilder message = new StringBuilder();
		message.append(
				String.format("Multiple Spring Session store implementations are available on the classpath:%n"));
		for (Class<?> candidate : cause.getAvailableCandidates()) {
			message.append(String.format("    - %s%n", candidate.getName()));
		}
		StringBuilder action = new StringBuilder();
		action.append(String.format("Consider any of the following:%n"));
		action.append(
				String.format("    - Define the `spring.session.store-type` property to the store you want to use%n"));
		action.append(String.format("    - Review your classpath and remove the unwanted store implementation(s)%n"));
		return new FailureAnalysis(message.toString(), action.toString(), cause);
	}

}
