package org.springframework.boot.actuate.autoconfigure.metrics;

import io.micrometer.core.instrument.config.validate.Validated.Invalid;
import io.micrometer.core.instrument.config.validate.ValidationException;

import org.springframework.boot.diagnostics.AbstractFailureAnalyzer;
import org.springframework.boot.diagnostics.FailureAnalysis;

/**
 * An {@link AbstractFailureAnalyzer} that performs analysis of failures caused by a
 * {@link ValidationException}.
 *

 */
class ValidationFailureAnalyzer extends AbstractFailureAnalyzer<ValidationException> {

	@Override
	protected FailureAnalysis analyze(Throwable rootFailure, ValidationException cause) {
		StringBuilder description = new StringBuilder(String.format("Invalid Micrometer configuration detected:%n"));
		for (Invalid<?> failure : cause.getValidation().failures()) {
			description.append(String.format("%n  - %s was '%s' but it %s", failure.getProperty(), failure.getValue(),
					failure.getMessage()));
		}
		return new FailureAnalysis(description.toString(),
				"Update your application to correct the invalid configuration.", cause);
	}

}
