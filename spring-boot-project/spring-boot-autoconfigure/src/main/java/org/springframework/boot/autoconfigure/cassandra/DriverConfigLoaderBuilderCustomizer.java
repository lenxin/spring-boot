package org.springframework.boot.autoconfigure.cassandra;

import com.datastax.oss.driver.api.core.config.DriverConfigLoader;
import com.datastax.oss.driver.api.core.config.ProgrammaticDriverConfigLoaderBuilder;

/**
 * Callback interface that can be implemented by beans wishing to customize the
 * {@link DriverConfigLoader} via a {@link DriverConfigLoaderBuilderCustomizer} whilst
 * retaining default auto-configuration.
 *

 * @since 2.3.0
 */
public interface DriverConfigLoaderBuilderCustomizer {

	/**
	 * Customize the {@linkplain ProgrammaticDriverConfigLoaderBuilder DriverConfigLoader
	 * builder}.
	 * @param builder the builder to customize
	 */
	void customize(ProgrammaticDriverConfigLoaderBuilder builder);

}
