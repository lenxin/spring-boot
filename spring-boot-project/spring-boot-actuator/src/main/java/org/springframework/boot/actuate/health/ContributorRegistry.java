package org.springframework.boot.actuate.health;

/**
 * A mutable registry of health endpoint contributors (either {@link HealthContributor} or
 * {@link ReactiveHealthContributor}).
 *
 * @param <C> the contributor type




 * @since 2.2.0
 * @see NamedContributors
 */
public interface ContributorRegistry<C> extends NamedContributors<C> {

	/**
	 * Register a contributor with the given {@code name}.
	 * @param name the name of the contributor
	 * @param contributor the contributor to register
	 * @throws IllegalStateException if the contributor cannot be registered with the
	 * given {@code name}.
	 */
	void registerContributor(String name, C contributor);

	/**
	 * Unregister a previously registered contributor.
	 * @param name the name of the contributor to unregister
	 * @return the unregistered indicator, or {@code null} if no indicator was found in
	 * the registry for the given {@code name}.
	 */
	C unregisterContributor(String name);

}
