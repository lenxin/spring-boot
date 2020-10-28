package org.springframework.boot.autoconfigure.flyway;

import org.springframework.boot.diagnostics.AbstractFailureAnalyzer;
import org.springframework.boot.diagnostics.FailureAnalysis;

/**
 * A {@code FailureAnalyzer} that performs analysis of failures caused by a
 * {@link FlywayMigrationScriptMissingException}.
 *


 */
class FlywayMigrationScriptMissingFailureAnalyzer
		extends AbstractFailureAnalyzer<FlywayMigrationScriptMissingException> {

	@Override
	protected FailureAnalysis analyze(Throwable rootFailure, FlywayMigrationScriptMissingException cause) {
		StringBuilder description = new StringBuilder("Flyway failed to initialize: ");
		if (cause.getLocations().isEmpty()) {
			return new FailureAnalysis(description.append("no migration scripts location is configured").toString(),
					"Check your Flyway configuration", cause);
		}
		description.append(String.format("none of the following migration scripts locations could be found:%n%n"));
		cause.getLocations().forEach((location) -> description.append(String.format("\t- %s%n", location)));
		return new FailureAnalysis(description.toString(),
				"Review the locations above or check your Flyway configuration", cause);
	}

}
