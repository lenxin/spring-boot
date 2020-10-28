package org.springframework.boot.actuate.metrics.web.reactive.client;

import java.util.Arrays;

import io.micrometer.core.instrument.Tag;

import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;

/**
 * Default implementation of {@link WebClientExchangeTagsProvider}.
 *


 * @since 2.1.0
 */
public class DefaultWebClientExchangeTagsProvider implements WebClientExchangeTagsProvider {

	@Override
	public Iterable<Tag> tags(ClientRequest request, ClientResponse response, Throwable throwable) {
		Tag method = WebClientExchangeTags.method(request);
		Tag uri = WebClientExchangeTags.uri(request);
		Tag clientName = WebClientExchangeTags.clientName(request);
		Tag status = WebClientExchangeTags.status(response, throwable);
		Tag outcome = WebClientExchangeTags.outcome(response);
		return Arrays.asList(method, uri, clientName, status, outcome);
	}

}
