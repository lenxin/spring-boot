package org.springframework.boot.logging;

import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;

/**
 * Context passed to the {@link LoggingSystem} during initialization.
 *

 * @since 1.3.0
 */
public class LoggingInitializationContext {

	private final ConfigurableEnvironment environment;

	/**
	 * Create a new {@link LoggingInitializationContext} instance.
	 * @param environment the Spring environment.
	 */
	public LoggingInitializationContext(ConfigurableEnvironment environment) {
		this.environment = environment;
	}

	/**
	 * Return the Spring environment if available.
	 * @return the {@link Environment} or {@code null}
	 */
	public Environment getEnvironment() {
		return this.environment;
	}

}
