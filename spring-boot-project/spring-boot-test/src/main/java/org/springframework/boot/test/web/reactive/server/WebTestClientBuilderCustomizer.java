package org.springframework.boot.test.web.reactive.server;

import org.springframework.test.web.reactive.server.WebTestClient.Builder;

/**
 * A customizer for a {@link Builder}. Any {@code WebTestClientBuilderCustomizer} beans
 * found in the application context will be {@link #customize called} to customize the
 * auto-configured {@link Builder}.
 *

 * @since 2.2.0
 */
@FunctionalInterface
public interface WebTestClientBuilderCustomizer {

	/**
	 * Customize the given {@code builder}.
	 * @param builder the builder
	 */
	void customize(Builder builder);

}
