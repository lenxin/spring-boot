package org.springframework.boot.actuate.autoconfigure.health;

import java.util.Collection;
import java.util.Map;

import org.springframework.boot.actuate.health.DefaultHealthContributorRegistry;
import org.springframework.boot.actuate.health.HealthContributor;
import org.springframework.boot.actuate.health.HealthContributorRegistry;
import org.springframework.util.Assert;

/**
 * An auto-configured {@link HealthContributorRegistry} that ensures registered indicators
 * do not clash with groups names.
 *

 */
class AutoConfiguredHealthContributorRegistry extends DefaultHealthContributorRegistry {

	private final Collection<String> groupNames;

	AutoConfiguredHealthContributorRegistry(Map<String, HealthContributor> contributors,
			Collection<String> groupNames) {
		super(contributors);
		this.groupNames = groupNames;
		contributors.keySet().forEach(this::assertDoesNotClashWithGroup);
	}

	@Override
	public void registerContributor(String name, HealthContributor contributor) {
		assertDoesNotClashWithGroup(name);
		super.registerContributor(name, contributor);
	}

	private void assertDoesNotClashWithGroup(String name) {
		Assert.state(!this.groupNames.contains(name),
				() -> "HealthContributor with name \"" + name + "\" clashes with group");
	}

}
