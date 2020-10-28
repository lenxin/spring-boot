package org.springframework.boot.actuate.metrics.web.reactive.client;

import io.micrometer.core.instrument.Tag;

import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;

/**
 * {@link Tag Tags} provider for an exchange performed by a
 * {@link org.springframework.web.reactive.function.client.WebClient}.
 *

 * @since 2.1.0
 */
@FunctionalInterface
public interface WebClientExchangeTagsProvider {

	/**
	 * Provide tags to be associated with metrics for the client exchange.
	 * @param request the client request
	 * @param response the server response (may be {@code null})
	 * @param throwable the exception (may be {@code null})
	 * @return tags to associate with metrics for the request and response exchange
	 */
	Iterable<Tag> tags(ClientRequest request, ClientResponse response, Throwable throwable);

}
