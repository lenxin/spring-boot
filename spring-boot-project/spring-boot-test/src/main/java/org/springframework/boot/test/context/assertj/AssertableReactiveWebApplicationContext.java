package org.springframework.boot.test.context.assertj;

import java.util.function.Supplier;

import org.springframework.boot.web.reactive.context.ConfigurableReactiveWebApplicationContext;
import org.springframework.boot.web.reactive.context.ReactiveWebApplicationContext;

/**
 * A {@link ReactiveWebApplicationContext} that additionally supports AssertJ style
 * assertions. Can be used to decorate an existing reactive web application context or an
 * application context that failed to start.
 * <p>
 * See {@link ApplicationContextAssertProvider} for more details.
 *

 * @since 2.0.0
 * @see ReactiveWebApplicationContext
 * @see ReactiveWebApplicationContext
 */
public interface AssertableReactiveWebApplicationContext
		extends ApplicationContextAssertProvider<ConfigurableReactiveWebApplicationContext>,
		ConfigurableReactiveWebApplicationContext {

	/**
	 * Factory method to create a new {@link AssertableReactiveWebApplicationContext}
	 * instance.
	 * @param contextSupplier a supplier that will either return a fully configured
	 * {@link ConfigurableReactiveWebApplicationContext} or throw an exception if the
	 * context fails to start.
	 * @return a {@link AssertableReactiveWebApplicationContext} instance
	 */
	static AssertableReactiveWebApplicationContext get(
			Supplier<? extends ConfigurableReactiveWebApplicationContext> contextSupplier) {
		return ApplicationContextAssertProvider.get(AssertableReactiveWebApplicationContext.class,
				ConfigurableReactiveWebApplicationContext.class, contextSupplier);
	}

}
