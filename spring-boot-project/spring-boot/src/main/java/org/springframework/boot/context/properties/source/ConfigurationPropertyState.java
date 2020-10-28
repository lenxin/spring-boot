package org.springframework.boot.context.properties.source;

import java.util.function.Predicate;

import org.springframework.util.Assert;

/**
 * The state of content from a {@link ConfigurationPropertySource}.
 *

 * @since 2.0.0
 */
public enum ConfigurationPropertyState {

	/**
	 * The {@link ConfigurationPropertySource} has at least one matching
	 * {@link ConfigurationProperty}.
	 */
	PRESENT,

	/**
	 * The {@link ConfigurationPropertySource} has no matching
	 * {@link ConfigurationProperty ConfigurationProperties}.
	 */
	ABSENT,

	/**
	 * It's not possible to determine if {@link ConfigurationPropertySource} has matching
	 * {@link ConfigurationProperty ConfigurationProperties} or not.
	 */
	UNKNOWN;

	/**
	 * Search the given iterable using a predicate to determine if content is
	 * {@link #PRESENT} or {@link #ABSENT}.
	 * @param <T> the data type
	 * @param source the source iterable to search
	 * @param predicate the predicate used to test for presence
	 * @return {@link #PRESENT} if the iterable contains a matching item, otherwise
	 * {@link #ABSENT}.
	 */
	static <T> ConfigurationPropertyState search(Iterable<T> source, Predicate<T> predicate) {
		Assert.notNull(source, "Source must not be null");
		Assert.notNull(predicate, "Predicate must not be null");
		for (T item : source) {
			if (predicate.test(item)) {
				return PRESENT;
			}
		}
		return ABSENT;
	}

}
