package org.springframework.boot.actuate.autoconfigure.health;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Member predicate that matches based on {@code include} and {@code exclude} sets.
 *

 */
class IncludeExcludeGroupMemberPredicate implements Predicate<String> {

	private final Set<String> include;

	private final Set<String> exclude;

	IncludeExcludeGroupMemberPredicate(Set<String> include, Set<String> exclude) {
		this.include = clean(include);
		this.exclude = clean(exclude);
	}

	@Override
	public boolean test(String name) {
		return isIncluded(name) && !isExcluded(name);
	}

	private boolean isIncluded(String name) {
		return this.include.isEmpty() || this.include.contains("*") || this.include.contains(clean(name));
	}

	private boolean isExcluded(String name) {
		return this.exclude.contains("*") || this.exclude.contains(clean(name));
	}

	private Set<String> clean(Set<String> names) {
		if (names == null) {
			return Collections.emptySet();
		}
		Set<String> cleaned = names.stream().map(this::clean).collect(Collectors.toCollection(LinkedHashSet::new));
		return Collections.unmodifiableSet(cleaned);
	}

	private String clean(String name) {
		return name.trim();
	}

}
