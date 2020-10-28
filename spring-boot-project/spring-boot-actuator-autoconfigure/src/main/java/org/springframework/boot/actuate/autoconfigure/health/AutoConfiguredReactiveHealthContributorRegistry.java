package org.springframework.boot.actuate.autoconfigure.health;

import java.util.Collection;
import java.util.Map;

import org.springframework.boot.actuate.health.DefaultReactiveHealthContributorRegistry;
import org.springframework.boot.actuate.health.HealthContributorRegistry;
import org.springframework.boot.actuate.health.ReactiveHealthContributor;
import org.springframework.util.Assert;

/**
 * An auto-configured {@link HealthContributorRegistry} that ensures registered indicators
 * do not clash with groups names.
 *

 */
class AutoConfiguredReactiveHealthContributorRegistry extends DefaultReactiveHealthContributorRegistry {

	private final Collection<String> groupNames;

	AutoConfiguredReactiveHealthContributorRegistry(Map<String, ReactiveHealthContributor> contributors,
			Collection<String> groupNames) {
		super(contributors);
		this.groupNames = groupNames;
		contributors.keySet().forEach(this::assertDoesNotClashWithGroup);
	}

	@Override
	public void registerContributor(String name, ReactiveHealthContributor contributor) {
		assertDoesNotClashWithGroup(name);
		super.registerContributor(name, contributor);
	}

	private void assertDoesNotClashWithGroup(String name) {
		Assert.state(!this.groupNames.contains(name),
				() -> "ReactiveHealthContributor with name \"" + name + "\" clashes with group");
	}

}
