package org.springframework.boot.autoconfigure.neo4j;

import org.neo4j.driver.Config;
import org.neo4j.driver.Config.ConfigBuilder;

/**
 * Callback interface that can be implemented by beans wishing to customize the
 * {@link Config} via a {@link ConfigBuilder} whilst retaining default auto-configuration.
 *

 * @since 2.4.0
 */
@FunctionalInterface
public interface ConfigBuilderCustomizer {

	/**
	 * Customize the {@link ConfigBuilder}.
	 * @param configBuilder the {@link ConfigBuilder} to customize
	 */
	void customize(ConfigBuilder configBuilder);

}
