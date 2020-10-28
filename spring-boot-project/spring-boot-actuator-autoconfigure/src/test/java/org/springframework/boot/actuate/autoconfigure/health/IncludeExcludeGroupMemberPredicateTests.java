package org.springframework.boot.actuate.autoconfigure.health;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link IncludeExcludeGroupMemberPredicate}.
 *


 */
class IncludeExcludeGroupMemberPredicateTests {

	@Test
	void testWhenEmptyIncludeAndExcludeAcceptsAll() {
		Predicate<String> predicate = new IncludeExcludeGroupMemberPredicate(null, null);
		assertThat(predicate).accepts("a", "b", "c");
	}

	@Test
	void testWhenStarIncludeAndEmptyExcludeAcceptsAll() {
		Predicate<String> predicate = include("*").exclude();
		assertThat(predicate).accepts("a", "b", "c");
	}

	@Test
	void testWhenEmptyIncludeAndNonEmptyExcludeAcceptsAllButExclude() {
		Predicate<String> predicate = new IncludeExcludeGroupMemberPredicate(null, Collections.singleton("c"));
		assertThat(predicate).accepts("a", "b");
	}

	@Test
	void testWhenStarIncludeAndSpecificExcludeDoesNotAcceptExclude() {
		Predicate<String> predicate = include("*").exclude("c");
		assertThat(predicate).accepts("a", "b").rejects("c");
	}

	@Test
	void testWhenSpecificIncludeAcceptsOnlyIncluded() {
		Predicate<String> predicate = include("a", "b").exclude();
		assertThat(predicate).accepts("a", "b").rejects("c");
	}

	@Test
	void testWhenSpecifiedIncludeAndSpecifiedExcludeAcceptsAsExpected() {
		Predicate<String> predicate = include("a", "b", "c").exclude("c");
		assertThat(predicate).accepts("a", "b").rejects("c", "d");
	}

	@Test
	void testWhenSpecifiedIncludeAndStarExcludeRejectsAll() {
		Predicate<String> predicate = include("a", "b", "c").exclude("*");
		assertThat(predicate).rejects("a", "b", "c", "d");
	}

	@Test
	void testWhenCamelCaseIncludeAcceptsOnlyIncluded() {
		Predicate<String> predicate = include("myEndpoint").exclude();
		assertThat(predicate).accepts("myEndpoint").rejects("d");
	}

	@Test
	void testWhenHyphenCaseIncludeAcceptsOnlyIncluded() {
		Predicate<String> predicate = include("my-endpoint").exclude();
		assertThat(predicate).accepts("my-endpoint").rejects("d");
	}

	@Test
	void testWhenExtraWhitespaceAcceptsTrimmedVersion() {
		Predicate<String> predicate = include("  myEndpoint  ").exclude();
		assertThat(predicate).accepts("myEndpoint").rejects("d");
	}

	private Builder include(String... include) {
		return new Builder(include);
	}

	private static class Builder {

		private final String[] include;

		Builder(String[] include) {
			this.include = include;
		}

		Predicate<String> exclude(String... exclude) {
			return new IncludeExcludeGroupMemberPredicate(asSet(this.include), asSet(exclude));
		}

		private Set<String> asSet(String[] names) {
			return (names != null) ? new LinkedHashSet<>(Arrays.asList(names)) : null;
		}

	}

}
