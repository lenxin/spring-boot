package org.springframework.boot.actuate.metrics.web.servlet;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.Tags;

/**
 * Default implementation of {@link WebMvcTagsProvider}.
 *

 * @since 2.0.0
 */
public class DefaultWebMvcTagsProvider implements WebMvcTagsProvider {

	private final boolean ignoreTrailingSlash;

	private final List<WebMvcTagsContributor> contributors;

	public DefaultWebMvcTagsProvider() {
		this(false);
	}

	/**
	 * Creates a new {@link DefaultWebMvcTagsProvider} that will provide tags from the
	 * given {@code contributors} in addition to its own.
	 * @param contributors the contributors that will provide additional tags
	 * @since 2.3.0
	 */
	public DefaultWebMvcTagsProvider(List<WebMvcTagsContributor> contributors) {
		this(false, contributors);
	}

	public DefaultWebMvcTagsProvider(boolean ignoreTrailingSlash) {
		this(ignoreTrailingSlash, Collections.emptyList());
	}

	/**
	 * Creates a new {@link DefaultWebMvcTagsProvider} that will provide tags from the
	 * given {@code contributors} in addition to its own.
	 * @param ignoreTrailingSlash whether trailing slashes should be ignored when
	 * determining the {@code uri} tag.
	 * @param contributors the contributors that will provide additional tags
	 * @since 2.3.0
	 */
	public DefaultWebMvcTagsProvider(boolean ignoreTrailingSlash, List<WebMvcTagsContributor> contributors) {
		this.ignoreTrailingSlash = ignoreTrailingSlash;
		this.contributors = contributors;
	}

	@Override
	public Iterable<Tag> getTags(HttpServletRequest request, HttpServletResponse response, Object handler,
			Throwable exception) {
		Tags tags = Tags.of(WebMvcTags.method(request), WebMvcTags.uri(request, response, this.ignoreTrailingSlash),
				WebMvcTags.exception(exception), WebMvcTags.status(response), WebMvcTags.outcome(response));
		for (WebMvcTagsContributor contributor : this.contributors) {
			tags = tags.and(contributor.getTags(request, response, handler, exception));
		}
		return tags;
	}

	@Override
	public Iterable<Tag> getLongRequestTags(HttpServletRequest request, Object handler) {
		Tags tags = Tags.of(WebMvcTags.method(request), WebMvcTags.uri(request, null, this.ignoreTrailingSlash));
		for (WebMvcTagsContributor contributor : this.contributors) {
			tags = tags.and(contributor.getLongRequestTags(request, handler));
		}
		return tags;
	}

}
