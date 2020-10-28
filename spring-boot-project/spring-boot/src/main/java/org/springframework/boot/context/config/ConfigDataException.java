package org.springframework.boot.context.config;

/**
 * Abstract base class for configuration data exceptions.
 *


 * @since 2.4.0
 */
public abstract class ConfigDataException extends RuntimeException {

	/**
	 * Create a new {@link ConfigDataException} instance.
	 * @param message the exception message
	 * @param cause the exception cause
	 */
	protected ConfigDataException(String message, Throwable cause) {
		super(message, cause);
	}

}
