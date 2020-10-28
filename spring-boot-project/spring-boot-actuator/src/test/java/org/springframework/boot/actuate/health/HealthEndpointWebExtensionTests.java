package org.springframework.boot.actuate.health;

import java.util.Collections;
import java.util.Map;

import org.junit.jupiter.api.Test;

import org.springframework.boot.actuate.endpoint.SecurityContext;
import org.springframework.boot.actuate.endpoint.http.ApiVersion;
import org.springframework.boot.actuate.endpoint.web.WebEndpointResponse;
import org.springframework.boot.actuate.health.HealthEndpointSupport.HealthResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

/**
 * Tests for {@link HealthEndpointWebExtension}.
 *


 */
class HealthEndpointWebExtensionTests
		extends HealthEndpointSupportTests<HealthContributorRegistry, HealthContributor, HealthComponent> {

	@Test
	void healthReturnsSystemHealth() {
		this.registry.registerContributor("test", createContributor(this.up));
		WebEndpointResponse<HealthComponent> response = create(this.registry, this.groups).health(ApiVersion.LATEST,
				SecurityContext.NONE);
		HealthComponent health = response.getBody();
		assertThat(health.getStatus()).isEqualTo(Status.UP);
		assertThat(health).isInstanceOf(SystemHealth.class);
		assertThat(response.getStatus()).isEqualTo(200);
	}

	@Test
	void healthWithNoContributorReturnsUp() {
		assertThat(this.registry).isEmpty();
		WebEndpointResponse<HealthComponent> response = create(this.registry,
				HealthEndpointGroups.of(mock(HealthEndpointGroup.class), Collections.emptyMap()))
						.health(ApiVersion.LATEST, SecurityContext.NONE);
		assertThat(response.getStatus()).isEqualTo(200);
		HealthComponent health = response.getBody();
		assertThat(health.getStatus()).isEqualTo(Status.UP);
		assertThat(health).isInstanceOf(Health.class);
	}

	@Test
	void healthWhenPathDoesNotExistReturnsHttp404() {
		this.registry.registerContributor("test", createContributor(this.up));
		WebEndpointResponse<HealthComponent> response = create(this.registry, this.groups).health(ApiVersion.LATEST,
				SecurityContext.NONE, "missing");
		assertThat(response.getBody()).isNull();
		assertThat(response.getStatus()).isEqualTo(404);
	}

	@Test
	void healthWhenPathExistsReturnsHealth() {
		this.registry.registerContributor("test", createContributor(this.up));
		WebEndpointResponse<HealthComponent> response = create(this.registry, this.groups).health(ApiVersion.LATEST,
				SecurityContext.NONE, "test");
		assertThat(response.getBody()).isEqualTo(this.up);
		assertThat(response.getStatus()).isEqualTo(200);
	}

	@Override
	protected HealthEndpointWebExtension create(HealthContributorRegistry registry, HealthEndpointGroups groups) {
		return new HealthEndpointWebExtension(registry, groups);
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
