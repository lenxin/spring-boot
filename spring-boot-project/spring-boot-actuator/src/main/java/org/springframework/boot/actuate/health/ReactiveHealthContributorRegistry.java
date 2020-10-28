package org.springframework.boot.actuate.health;

/**
 * {@link ContributorRegistry} for {@link ReactiveHealthContributor
 * ReactiveHealthContributors}.
 *

 * @since 2.2.0
 */
public interface ReactiveHealthContributorRegistry extends ContributorRegistry<ReactiveHealthContributor> {

}
