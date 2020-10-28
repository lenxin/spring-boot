package org.springframework.boot.actuate.endpoint.invoke;

import org.springframework.boot.actuate.endpoint.InvocationContext;

/**
 * Interface to perform an operation invocation.
 *


 * @since 2.0.0
 */
@FunctionalInterface
public interface OperationInvoker {

	/**
	 * Invoke the underlying operation using the given {@code context}.
	 * @param context the context to use to invoke the operation
	 * @return the result of the operation, may be {@code null}
	 * @throws MissingParametersException if parameters are missing
	 */
	Object invoke(InvocationContext context) throws MissingParametersException;

}
