package org.springframework.boot.logging;

import org.springframework.core.io.support.SpringFactoriesLoader;

/**
 * Factory class used by {@link LoggingSystem#get(ClassLoader)} to find an actual
 * implementation.
 *

 * @since 2.4.0
 */
public interface LoggingSystemFactory {

	/**
	 * Return a logging system implementation or {@code null} if no logging system is
	 * available.
	 * @param classLoader the class loader to use
	 * @return a logging system
	 */
	LoggingSystem getLoggingSystem(ClassLoader classLoader);

	/**
	 * Return a {@link LoggingSystemFactory} backed by {@code spring.factories}.
	 * @return a {@link LoggingSystemFactory} instance
	 */
	static LoggingSystemFactory fromSpringFactories() {
		return new DelegatingLoggingSystemFactory(
				(classLoader) -> SpringFactoriesLoader.loadFactories(LoggingSystemFactory.class, classLoader));
	}

}
