package org.springframework.boot.autoconfigure.flyway;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import org.springframework.boot.diagnostics.FailureAnalysis;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link FlywayMigrationScriptMissingFailureAnalyzer}.
 *

 */
class FlywayMigrationScriptMissingFailureAnalyzerTests {

	@Test
	void analysisForMissingScriptLocation() {
		FailureAnalysis failureAnalysis = performAnalysis();
		assertThat(failureAnalysis.getDescription()).contains("no migration scripts location is configured");
		assertThat(failureAnalysis.getAction()).contains("Check your Flyway configuration");
	}

	@Test
	void analysisForScriptLocationsNotFound() {
		FailureAnalysis failureAnalysis = performAnalysis("classpath:db/migration");
		assertThat(failureAnalysis.getDescription())
				.contains("none of the following migration scripts locations could be found")
				.contains("classpath:db/migration");
		assertThat(failureAnalysis.getAction())
				.contains("Review the locations above or check your Flyway configuration");
	}

	private FailureAnalysis performAnalysis(String... locations) {
		FlywayMigrationScriptMissingException exception = new FlywayMigrationScriptMissingException(
				Arrays.asList(locations));
		return new FlywayMigrationScriptMissingFailureAnalyzer().analyze(exception);
	}

}
