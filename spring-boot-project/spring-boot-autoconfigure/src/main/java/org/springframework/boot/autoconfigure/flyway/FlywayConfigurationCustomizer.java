package org.springframework.boot.autoconfigure.flyway;

import org.flywaydb.core.api.configuration.FluentConfiguration;

/**
 * Callback interface that can be implemented by beans wishing to customize the flyway
 * configuration.
 *

 * @since 2.1.0
 */
@FunctionalInterface
public interface FlywayConfigurationCustomizer {

	/**
	 * Customize the flyway configuration.
	 * @param configuration the {@link FluentConfiguration} to customize
	 */
	void customize(FluentConfiguration configuration);

}
