package org.springframework.boot.diagnostics;

import org.springframework.core.ResolvableType;

/**
 * Abstract base class for most {@code FailureAnalyzer} implementations.
 *
 * @param <T> the type of exception to analyze


 * @since 1.4.0
 */
public abstract class AbstractFailureAnalyzer<T extends Throwable> implements FailureAnalyzer {

	@Override
	public FailureAnalysis analyze(Throwable failure) {
		T cause = findCause(failure, getCauseType());
		if (cause != null) {
			return analyze(failure, cause);
		}
		return null;
	}

	/**
	 * Returns an analysis of the given {@code rootFailure}, or {@code null} if no
	 * analysis was possible.
	 * @param rootFailure the root failure passed to the analyzer
	 * @param cause the actual found cause
	 * @return the analysis or {@code null}
	 */
	protected abstract FailureAnalysis analyze(Throwable rootFailure, T cause);

	/**
	 * Return the cause type being handled by the analyzer. By default the class generic
	 * is used.
	 * @return the cause type
	 */
	@SuppressWarnings("unchecked")
	protected Class<? extends T> getCauseType() {
		return (Class<? extends T>) ResolvableType.forClass(AbstractFailureAnalyzer.class, getClass()).resolveGeneric();
	}

	@SuppressWarnings("unchecked")
	protected final <E extends Throwable> E findCause(Throwable failure, Class<E> type) {
		while (failure != null) {
			if (type.isInstance(failure)) {
				return (E) failure;
			}
			failure = failure.getCause();
		}
		return null;
	}

}
