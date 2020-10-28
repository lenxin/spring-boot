package org.springframework.boot.autoconfigure.web.reactive;

import org.springframework.web.reactive.config.ResourceHandlerRegistration;

/**
 * Callback interface that can be used to customize {@link ResourceHandlerRegistration}.
 *

 * @since 2.1.0
 */
@FunctionalInterface
public interface ResourceHandlerRegistrationCustomizer {

	/**
	 * Customize the given {@link ResourceHandlerRegistration}.
	 * @param registration the registration to customize
	 */
	void customize(ResourceHandlerRegistration registration);

}
