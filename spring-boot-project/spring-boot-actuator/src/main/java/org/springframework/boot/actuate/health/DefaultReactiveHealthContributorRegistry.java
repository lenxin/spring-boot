package org.springframework.boot.actuate.health;

import java.util.Map;
import java.util.function.Function;

/**
 * Default {@link ReactiveHealthContributorRegistry} implementation.
 *

 * @since 2.0.0
 */
public class DefaultReactiveHealthContributorRegistry extends DefaultContributorRegistry<ReactiveHealthContributor>
		implements ReactiveHealthContributorRegistry {

	public DefaultReactiveHealthContributorRegistry() {
	}

	public DefaultReactiveHealthContributorRegistry(Map<String, ReactiveHealthContributor> contributors) {
		super(contributors);
	}

	public DefaultReactiveHealthContributorRegistry(Map<String, ReactiveHealthContributor> contributors,
			Function<String, String> nameFactory) {
		super(contributors, nameFactory);
	}

}
