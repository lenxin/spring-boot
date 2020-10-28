package org.springframework.boot.context.config;

import org.springframework.boot.ConfigurableBootstrapContext;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.env.EnvironmentPostProcessor;

/**
 * Context provided to {@link ConfigDataLocationResolver} methods.
 *


 * @since 2.4.0
 */
public interface ConfigDataLocationResolverContext {

	/**
	 * Provides access to a binder that can be used to obtain previously contributed
	 * values.
	 * @return a binder instance
	 */
	Binder getBinder();

	/**
	 * Provides access to the parent {@link ConfigDataResource} that triggered the resolve
	 * or {@code null} if there is no available parent.
	 * @return the parent location
	 */
	ConfigDataResource getParent();

	/**
	 * Provides access to the {@link ConfigurableBootstrapContext} shared across all
	 * {@link EnvironmentPostProcessor EnvironmentPostProcessors}.
	 * @return the bootstrap context
	 */
	ConfigurableBootstrapContext getBootstrapContext();

}
