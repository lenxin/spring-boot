package org.springframework.boot.autoconfigure.r2dbc;

import io.r2dbc.spi.ConnectionFactoryOptions;
import io.r2dbc.spi.ConnectionFactoryOptions.Builder;

/**
 * Callback interface that can be implemented by beans wishing to customize the
 * {@link ConnectionFactoryOptions} via a {@link Builder} whilst retaining default
 * auto-configuration.whilst retaining default auto-configuration.
 *

 * @since 2.3.0
 */
@FunctionalInterface
public interface ConnectionFactoryOptionsBuilderCustomizer {

	/**
	 * Customize the {@link Builder}.
	 * @param builder the builder to customize
	 */
	void customize(Builder builder);

}
