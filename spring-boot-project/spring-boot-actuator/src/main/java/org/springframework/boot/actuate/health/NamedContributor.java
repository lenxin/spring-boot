package org.springframework.boot.actuate.health;

import org.springframework.util.Assert;

/**
 * A single named health endpoint contributors (either {@link HealthContributor} or
 * {@link ReactiveHealthContributor}).
 *
 * @param <C> the contributor type

 * @since 2.0.0
 * @see NamedContributors
 */
public interface NamedContributor<C> {

	/**
	 * Returns the name of the contributor.
	 * @return the contributor name
	 */
	String getName();

	/**
	 * Returns the contributor instance.
	 * @return the contributor instance
	 */
	C getContributor();

	static <C> NamedContributor<C> of(String name, C contributor) {
		Assert.notNull(name, "Name must not be null");
		Assert.notNull(contributor, "Contributor must not be null");
		return new NamedContributor<C>() {

			@Override
			public String getName() {
				return name;
			}

			@Override
			public C getContributor() {
				return contributor;
			}

		};
	}

}
