package org.springframework.boot.actuate.health;

import java.util.Collections;
import java.util.Map;

import org.junit.jupiter.api.Test;

import org.springframework.boot.actuate.health.HealthEndpointSupport.HealthResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

/**
 * Tests for {@link HealthEndpoint}.
 *


 */
class HealthEndpointTests
		extends HealthEndpointSupportTests<HealthContributorRegistry, HealthContributor, HealthComponent> {

	@Test
	void healthReturnsSystemHealth() {
		this.registry.registerContributor("test", createContributor(this.up));
		HealthComponent health = create(this.registry, this.groups).health();
		assertThat(health.getStatus()).isEqualTo(Status.UP);
		assertThat(health).isInstanceOf(SystemHealth.class);
	}

	@Test
	void healthWithNoContributorReturnsUp() {
		assertThat(this.registry).isEmpty();
		HealthComponent health = create(this.registry,
				HealthEndpointGroups.of(mock(HealthEndpointGroup.class), Collections.emptyMap())).health();
		assertThat(health.getStatus()).isEqualTo(Status.UP);
		assertThat(health).isInstanceOf(Health.class);
	}

	@Test
	void healthWhenPathDoesNotExistReturnsNull() {
		this.registry.registerContributor("test", createContributor(this.up));
		HealthComponent health = create(this.registry, this.groups).healthForPath("missing");
		assertThat(health).isNull();
	}

	@Test
	void healthWhenPathExistsReturnsHealth() {
		this.registry.registerContributor("test", createContributor(this.up));
		HealthComponent health = create(this.registry, this.groups).healthForPath("test");
		assertThat(health).isEqualTo(this.up);
	}

	@Override
	protected HealthEndpoint create(HealthContributorRegistry registry, HealthEndpointGroups groups) {
		return new HealthEndpoint(registry, groups);
	}

	@Override
	protected HealthContributorRegistry createRegistry() {
		return new DefaultHealthContributorRegistry();
	}

	@Override
	protected HealthContributor createContributor(Health health) {
		return (HealthIndicator) () -> health;
	}

	@Override
	protected HealthContributor createCompositeContributor(Map<String, HealthContributor> contributors) {
		return CompositeHealthContributor.fromMap(contributors);
	}

	@Override
	protected HealthComponent getHealth(HealthResult<HealthComponent> result) {
		return result.getHealth();
	}

}
