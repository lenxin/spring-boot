package org.springframework.boot.actuate.health;

import java.util.Map;
import java.util.function.Function;

/**
 * Default {@link HealthContributorRegistry} implementation.
 *

 * @since 2.2.0
 */
public class DefaultHealthContributorRegistry extends DefaultContributorRegistry<HealthContributor>
		implements HealthContributorRegistry {

	public DefaultHealthContributorRegistry() {
	}

	public DefaultHealthContributorRegistry(Map<String, HealthContributor> contributors) {
		super(contributors);
	}

	public DefaultHealthContributorRegistry(Map<String, HealthContributor> contributors,
			Function<String, String> nameFactory) {
		super(contributors, nameFactory);
	}

}
