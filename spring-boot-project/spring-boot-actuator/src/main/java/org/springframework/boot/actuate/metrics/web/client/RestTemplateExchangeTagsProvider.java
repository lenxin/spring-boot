package org.springframework.boot.actuate.metrics.web.client;

import io.micrometer.core.instrument.Tag;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.RestTemplate;

/**
 * Provides {@link Tag Tags} for an exchange performed by a {@link RestTemplate}.
 *


 * @since 2.0.0
 */
@FunctionalInterface
public interface RestTemplateExchangeTagsProvider {

	/**
	 * Provides the tags to be associated with metrics that are recorded for the given
	 * {@code request} and {@code response} exchange.
	 * @param urlTemplate the source URl template, if available
	 * @param request the request
	 * @param response the response (may be {@code null} if the exchange failed)
	 * @return the tags
	 */
	Iterable<Tag> getTags(String urlTemplate, HttpRequest request, ClientHttpResponse response);

}
