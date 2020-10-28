package org.springframework.boot.test.context.assertj;

import java.util.function.Supplier;

import org.springframework.boot.test.context.runner.WebApplicationContextRunner;
import org.springframework.web.context.ConfigurableWebApplicationContext;
import org.springframework.web.context.WebApplicationContext;

/**
 * A {@link WebApplicationContext} that additionally supports AssertJ style assertions.
 * Can be used to decorate an existing servlet web application context or an application
 * context that failed to start.
 * <p>
 * See {@link ApplicationContextAssertProvider} for more details.
 *

 * @since 2.0.0
 * @see WebApplicationContextRunner
 * @see WebApplicationContext
 */
public interface AssertableWebApplicationContext
		extends ApplicationContextAssertProvider<ConfigurableWebApplicationContext>, ConfigurableWebApplicationContext {

	/**
	 * Factory method to create a new {@link AssertableWebApplicationContext} instance.
	 * @param contextSupplier a supplier that will either return a fully configured
	 * {@link ConfigurableWebApplicationContext} or throw an exception if the context
	 * fails to start.
	 * @return a {@link AssertableWebApplicationContext} instance
	 */
	static AssertableWebApplicationContext get(Supplier<? extends ConfigurableWebApplicationContext> contextSupplier) {
		return ApplicationContextAssertProvider.get(AssertableWebApplicationContext.class,
				ConfigurableWebApplicationContext.class, contextSupplier);
	}

}
