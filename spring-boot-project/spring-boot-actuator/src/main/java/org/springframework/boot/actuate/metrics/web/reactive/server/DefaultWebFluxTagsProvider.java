package org.springframework.boot.actuate.metrics.web.reactive.server;

import java.util.Collections;
import java.util.List;

import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.Tags;

import org.springframework.web.server.ServerWebExchange;

/**
 * Default implementation of {@link WebFluxTagsProvider}.
 *


 * @since 2.0.0
 */
public class DefaultWebFluxTagsProvider implements WebFluxTagsProvider {

	private final boolean ignoreTrailingSlash;

	private final List<WebFluxTagsContributor> contributors;

	public DefaultWebFluxTagsProvider() {
		this(false);
	}

	/**
	 * Creates a new {@link DefaultWebFluxTagsProvider} that will provide tags from the
	 * given {@code contributors} in addition to its own.
	 * @param contributors the contributors that will provide additional tags
	 * @since 2.3.0
	 */
	public DefaultWebFluxTagsProvider(List<WebFluxTagsContributor> contributors) {
		this(false, contributors);
	}

	public DefaultWebFluxTagsProvider(boolean ignoreTrailingSlash) {
		this(ignoreTrailingSlash, Collections.emptyList());
	}

	/**
	 * Creates a new {@link DefaultWebFluxTagsProvider} that will provide tags from the
	 * given {@code contributors} in addition to its own.
	 * @param ignoreTrailingSlash wither trailing slashes should be ignored when
	 * determining the {@code uri} tag.
	 * @param contributors the contributors that will provide additional tags
	 * @since 2.3.0
	 */
	public DefaultWebFluxTagsProvider(boolean ignoreTrailingSlash, List<WebFluxTagsContributor> contributors) {
		this.ignoreTrailingSlash = ignoreTrailingSlash;
		this.contributors = contributors;
	}

	@Override
	public Iterable<Tag> httpRequestTags(ServerWebExchange exchange, Throwable exception) {
		Tags tags = Tags.of(WebFluxTags.method(exchange), WebFluxTags.uri(exchange, this.ignoreTrailingSlash),
				WebFluxTags.exception(exception), WebFluxTags.status(exchange), WebFluxTags.outcome(exchange));
		for (WebFluxTagsContributor contributor : this.contributors) {
			tags = tags.and(contributor.httpRequestTags(exchange, exception));
		}
		return tags;
	}

}
