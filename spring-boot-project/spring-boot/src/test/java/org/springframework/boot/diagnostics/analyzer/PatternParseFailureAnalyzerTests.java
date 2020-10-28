package org.springframework.boot.diagnostics.analyzer;

import org.junit.jupiter.api.Test;

import org.springframework.boot.diagnostics.FailureAnalysis;
import org.springframework.web.util.pattern.PathPatternParser;
import org.springframework.web.util.pattern.PatternParseException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link PatternParseFailureAnalyzer}
 *

 */
class PatternParseFailureAnalyzerTests {

	private PathPatternParser parser = new PathPatternParser();

	@Test
	void patternParseFailureQuotesPattern() {
		FailureAnalysis failureAnalysis = performAnalysis("/spring/**/framework");
		assertThat(failureAnalysis.getDescription()).contains("Invalid mapping pattern detected: /spring/**/framework");
		assertThat(failureAnalysis.getAction())
				.contains("Fix this pattern in your application or switch to the legacy parser"
						+ " implementation with `spring.mvc.pathpattern.matching-strategy=ant_path_matcher`.");
	}

	private FailureAnalysis performAnalysis(String pattern) {
		PatternParseException failure = createFailure(pattern);
		assertThat(failure).isNotNull();
		return new PatternParseFailureAnalyzer().analyze(failure);
	}

	PatternParseException createFailure(String pattern) {
		try {
			this.parser.parse(pattern);
			return null;
		}
		catch (PatternParseException ex) {
			return ex;
		}
	}

}
