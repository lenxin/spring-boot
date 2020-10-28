package org.springframework.boot.buildpack.platform.system;

/**
 * Provides access to environment variable values.
 *


 * @since 2.3.0
 */
@FunctionalInterface
public interface Environment {

	/**
	 * Standard {@link Environment} implementation backed by
	 * {@link System#getenv(String)}.
	 */
	Environment SYSTEM = System::getenv;

	/**
	 * Gets the value of the specified environment variable.
	 * @param name the name of the environment variable
	 * @return the string value of the variable, or {@code null} if the variable is not
	 * defined in the environment
	 */
	String get(String name);

}
