package org.springframework.boot.actuate.endpoint;

/**
 * An operation on an {@link ExposableEndpoint endpoint}.
 *


 * @since 2.0.0
 */
public interface Operation {

	/**
	 * Returns the {@link OperationType type} of the operation.
	 * @return the type
	 */
	OperationType getType();

	/**
	 * Invoke the underlying operation using the given {@code context}.
	 * @param context the context in to use when invoking the operation
	 * @return the result of the operation, may be {@code null}
	 */
	Object invoke(InvocationContext context);

}
