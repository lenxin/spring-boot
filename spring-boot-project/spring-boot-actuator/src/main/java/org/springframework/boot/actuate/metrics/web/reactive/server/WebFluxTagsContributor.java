package org.springframework.boot.actuate.metrics.web.reactive.server;

import io.micrometer.core.instrument.Tag;

import org.springframework.web.server.ServerWebExchange;

/**
 * A contributor of {@link Tag Tags} for WebFlux-based request handling. Typically used by
 * a {@link WebFluxTagsProvider} to provide tags in addition to its defaults.
 *

 * @since 2.3.0
 */
@FunctionalInterface
public interface WebFluxTagsContributor {

	/**
	 * Provides tags to be associated with metrics for the given {@code exchange}.
	 * @param exchange the exchange
	 * @param ex the current exception (may be {@code null})
	 * @return tags to associate with metrics for the request and response exchange
	 */
	Iterable<Tag> httpRequestTags(ServerWebExchange exchange, Throwable ex);

}
