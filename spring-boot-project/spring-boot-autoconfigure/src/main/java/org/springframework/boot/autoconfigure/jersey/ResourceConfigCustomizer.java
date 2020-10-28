package org.springframework.boot.autoconfigure.jersey;

import org.glassfish.jersey.server.ResourceConfig;

/**
 * Callback interface that can be implemented by beans wishing to customize Jersey's
 * {@link ResourceConfig} before it is used.
 *

 * @since 1.4.0
 */
@FunctionalInterface
public interface ResourceConfigCustomizer {

	/**
	 * Customize the resource config.
	 * @param config the {@link ResourceConfig} to customize
	 */
	void customize(ResourceConfig config);

}
