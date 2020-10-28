package org.springframework.boot.actuate.health;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * A collection of named health endpoint contributors (either {@link HealthContributor} or
 * {@link ReactiveHealthContributor}).
 *
 * @param <C> the contributor type

 * @since 2.0.0
 * @see NamedContributor
 */
public interface NamedContributors<C> extends Iterable<NamedContributor<C>> {

	/**
	 * Return the contributor with the given name.
	 * @param name the name of the contributor
	 * @return a contributor instance of {@code null}
	 */
	C getContributor(String name);

	/**
	 * Return a stream of the {@link NamedContributor named contributors}.
	 * @return the stream of named contributors
	 */
	default Stream<NamedContributor<C>> stream() {
		return StreamSupport.stream(spliterator(), false);
	}

}
