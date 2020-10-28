package org.springframework.boot.diagnostics;

/**
 * A {@code FailureAnalyzer} is used to analyze a failure and provide diagnostic
 * information that can be displayed to the user.
 *

 * @since 1.4.0
 */
@FunctionalInterface
public interface FailureAnalyzer {

	/**
	 * Returns an analysis of the given {@code failure}, or {@code null} if no analysis
	 * was possible.
	 * @param failure the failure
	 * @return the analysis or {@code null}
	 */
	FailureAnalysis analyze(Throwable failure);

}
