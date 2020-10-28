package org.springframework.boot.actuate.metrics.web.client;

import java.util.Arrays;

import io.micrometer.core.instrument.Tag;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StringUtils;

/**
 * Default implementation of {@link RestTemplateExchangeTagsProvider}.
 *


 * @since 2.0.0
 */
public class DefaultRestTemplateExchangeTagsProvider implements RestTemplateExchangeTagsProvider {

	@Override
	public Iterable<Tag> getTags(String urlTemplate, HttpRequest request, ClientHttpResponse response) {
		Tag uriTag = (StringUtils.hasText(urlTemplate) ? RestTemplateExchangeTags.uri(urlTemplate)
				: RestTemplateExchangeTags.uri(request));
		return Arrays.asList(RestTemplateExchangeTags.method(request), uriTag,
				RestTemplateExchangeTags.status(response), RestTemplateExchangeTags.clientName(request),
				RestTemplateExchangeTags.outcome(response));
	}

}
