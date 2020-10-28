package org.springframework.boot.autoconfigure.session;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import org.springframework.boot.diagnostics.FailureAnalysis;
import org.springframework.boot.diagnostics.FailureAnalyzer;
import org.springframework.boot.diagnostics.LoggingFailureAnalysisReporter;
import org.springframework.session.SessionRepository;
import org.springframework.session.hazelcast.HazelcastIndexedSessionRepository;
import org.springframework.session.jdbc.JdbcIndexedSessionRepository;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link NonUniqueSessionRepositoryFailureAnalyzer}.
 *

 */
class NonUniqueSessionRepositoryFailureAnalyzerTests {

	private final FailureAnalyzer analyzer = new NonUniqueSessionRepositoryFailureAnalyzer();

	@Test
	void failureAnalysisWithMultipleCandidates() {
		FailureAnalysis analysis = analyzeFailure(
				createFailure(JdbcIndexedSessionRepository.class, HazelcastIndexedSessionRepository.class));
		assertThat(analysis).isNotNull();
		assertThat(analysis.getDescription()).contains(JdbcIndexedSessionRepository.class.getName(),
				HazelcastIndexedSessionRepository.class.getName());
		assertThat(analysis.getAction()).contains("spring.session.store-type");
	}

	@SafeVarargs
	@SuppressWarnings("varargs")
	private final Exception createFailure(Class<? extends SessionRepository<?>>... candidates) {
		return new NonUniqueSessionRepositoryException(Arrays.asList(candidates));
	}

	private FailureAnalysis analyzeFailure(Exception failure) {
		FailureAnalysis analysis = this.analyzer.analyze(failure);
		if (analysis != null) {
			new LoggingFailureAnalysisReporter().report(analysis);
		}
		return analysis;
	}

}
