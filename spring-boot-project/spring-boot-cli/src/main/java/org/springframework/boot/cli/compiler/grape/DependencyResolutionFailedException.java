package org.springframework.boot.cli.compiler.grape;

/**
 * Thrown to indicate a failure during dependency resolution.
 *

 * @since 1.0.0
 */
@SuppressWarnings("serial")
public class DependencyResolutionFailedException extends RuntimeException {

	/**
	 * Creates a new {@code DependencyResolutionFailedException} with the given
	 * {@code cause}.
	 * @param cause the cause of the resolution failure
	 */
	public DependencyResolutionFailedException(Throwable cause) {
		super(cause);
	}

}
