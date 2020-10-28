package org.springframework.boot.context.config;

import org.springframework.boot.ConfigurableBootstrapContext;
import org.springframework.boot.env.EnvironmentPostProcessor;

/**
 * Context provided to {@link ConfigDataLoader} methods.
 *

 * @since 2.4.0
 */
public interface ConfigDataLoaderContext {

	/**
	 * Provides access to the {@link ConfigurableBootstrapContext} shared across all
	 * {@link EnvironmentPostProcessor EnvironmentPostProcessors}.
	 * @return the bootstrap context
	 */
	ConfigurableBootstrapContext getBootstrapContext();

}
