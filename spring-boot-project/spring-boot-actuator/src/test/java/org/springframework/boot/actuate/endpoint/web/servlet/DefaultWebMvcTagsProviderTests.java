package org.springframework.boot.actuate.endpoint.web.servlet;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.micrometer.core.instrument.Tag;
import org.junit.jupiter.api.Test;

import org.springframework.boot.actuate.metrics.web.servlet.DefaultWebMvcTagsProvider;
import org.springframework.boot.actuate.metrics.web.servlet.WebMvcTagsContributor;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link DefaultWebMvcTagsProvider}.
 *

 */
public class DefaultWebMvcTagsProviderTests {

	@Test
	void whenTagsAreProvidedThenDefaultTagsArePresent() {
		Map<String, Tag> tags = asMap(new DefaultWebMvcTagsProvider().getTags(null, null, null, null));
		assertThat(tags).containsOnlyKeys("exception", "method", "outcome", "status", "uri");
	}

	@Test
	void givenSomeContributorsWhenTagsAreProvidedThenDefaultTagsAndContributedTagsArePresent() {
		Map<String, Tag> tags = asMap(
				new DefaultWebMvcTagsProvider(Arrays.asList(new TestWebMvcTagsContributor("alpha"),
						new TestWebMvcTagsContributor("bravo", "charlie"))).getTags(null, null, null, null));
		assertThat(tags).containsOnlyKeys("exception", "method", "outcome", "status", "uri", "alpha", "bravo",
				"charlie");
	}

	@Test
	void whenLongRequestTagsAreProvidedThenDefaultTagsArePresent() {
		Map<String, Tag> tags = asMap(new DefaultWebMvcTagsProvider().getLongRequestTags(null, null));
		assertThat(tags).containsOnlyKeys("method", "uri");
	}

	@Test
	void givenSomeContributorsWhenLongRequestTagsAreProvidedThenDefaultTagsAndContributedTagsArePresent() {
		Map<String, Tag> tags = asMap(
				new DefaultWebMvcTagsProvider(Arrays.asList(new TestWebMvcTagsContributor("alpha"),
						new TestWebMvcTagsContributor("bravo", "charlie"))).getLongRequestTags(null, null));
		assertThat(tags).containsOnlyKeys("method", "uri", "alpha", "bravo", "charlie");
	}

	private Map<String, Tag> asMap(Iterable<Tag> tags) {
		return StreamSupport.stream(tags.spliterator(), false)
				.collect(Collectors.toMap(Tag::getKey, Function.identity()));
	}

	private static final class TestWebMvcTagsContributor implements WebMvcTagsContributor {

		private final List<String> tagNames;

		private TestWebMvcTagsContributor(String... tagNames) {
			this.tagNames = Arrays.asList(tagNames);
		}

		@Override
		public Iterable<Tag> getTags(HttpServletRequest request, HttpServletResponse response, Object handler,
				Throwable exception) {
			return this.tagNames.stream().map((name) -> Tag.of(name, "value")).collect(Collectors.toList());
		}

		@Override
		public Iterable<Tag> getLongRequestTags(HttpServletRequest request, Object handler) {
			return this.tagNames.stream().map((name) -> Tag.of(name, "value")).collect(Collectors.toList());
		}

	}

}
