package org.springframework.boot.logging;

import java.util.List;
import java.util.function.Function;

/**
 * {@link LoggingSystemFactory} that delegates to other factories.
 *

 */
class DelegatingLoggingSystemFactory implements LoggingSystemFactory {

	private final Function<ClassLoader, List<LoggingSystemFactory>> delegates;

	/**
	 * Create a new {@link DelegatingLoggingSystemFactory} instance.
	 * @param delegates a function that provides the delegates
	 */
	DelegatingLoggingSystemFactory(Function<ClassLoader, List<LoggingSystemFactory>> delegates) {
		this.delegates = delegates;
	}

	@Override
	public LoggingSystem getLoggingSystem(ClassLoader classLoader) {
		List<LoggingSystemFactory> delegates = (this.delegates != null) ? this.delegates.apply(classLoader) : null;
		if (delegates != null) {
			for (LoggingSystemFactory delegate : delegates) {
				LoggingSystem loggingSystem = delegate.getLoggingSystem(classLoader);
				if (loggingSystem != null) {
					return loggingSystem;
				}
			}
		}
		return null;
	}

}
