package org.springframework.boot.test.context.runner;

import org.springframework.context.ApplicationContext;

/**
 * Callback interface used to process an {@link ApplicationContext} with the ability to
 * throw a (checked) exception.
 *


 * @param <C> the application context type
 * @since 2.0.0
 * @see AbstractApplicationContextRunner
 */
@FunctionalInterface
public interface ContextConsumer<C extends ApplicationContext> {

	/**
	 * Performs this operation on the supplied {@code context}.
	 * @param context the application context to consume
	 * @throws Throwable any exception that might occur in assertions
	 */
	void accept(C context) throws Throwable;

}
