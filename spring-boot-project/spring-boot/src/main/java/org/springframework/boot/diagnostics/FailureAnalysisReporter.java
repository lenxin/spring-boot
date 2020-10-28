package org.springframework.boot.diagnostics;

/**
 * Reports a {@code FailureAnalysis} to the user.
 *

 * @since 1.4.0
 */
@FunctionalInterface
public interface FailureAnalysisReporter {

	/**
	 * Reports the given {@code failureAnalysis} to the user.
	 * @param analysis the analysis
	 */
	void report(FailureAnalysis analysis);

}
