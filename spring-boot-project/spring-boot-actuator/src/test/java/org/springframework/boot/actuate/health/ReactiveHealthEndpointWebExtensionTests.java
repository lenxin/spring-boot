package org.springframework.boot.actuate.health;

import java.util.Collections;
import java.util.Map;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

import org.springframework.boot.actuate.endpoint.SecurityContext;
import org.springframework.boot.actuate.endpoint.http.ApiVersion;
import org.springframework.boot.actuate.endpoint.web.WebEndpointResponse;
import org.springframework.boot.actuate.health.HealthEndpointSupport.HealthResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

/**
 * Tests for {@link ReactiveHealthEndpointWebExtension}.
 *


 */
class ReactiveHealthEndpointWebExtensionTests extends
		HealthEndpointSupportTests<ReactiveHealthContributorRegistry, ReactiveHealthContributor, Mono<? extends HealthComponent>> {

	@Test
	void healthReturnsSystemHealth() {
		this.registry.registerContributor("test", createContributor(this.up));
		WebEndpointResponse<? extends HealthComponent> response = create(this.registry, this.groups)
				.health(ApiVersion.LATEST, SecurityContext.NONE).block();
		HealthComponent health = response.getBody();
		assertThat(health.getStatus()).isEqualTo(Status.UP);
		assertThat(health).isInstanceOf(SystemHealth.class);
		assertThat(response.getStatus()).isEqualTo(200);
	}

	@Test
	void healthWithNoContributorReturnsUp() {
		assertThat(this.registry).isEmpty();
		WebEndpointResponse<? extends HealthComponent> response = create(this.registry,
				HealthEndpointGroups.of(mock(HealthEndpointGroup.class), Collections.emptyMap()))
						.health(ApiVersion.LATEST, SecurityContext.NONE).block();
		assertThat(response.getStatus()).isEqualTo(200);
		HealthComponent health = response.getBody();
		assertThat(health.getStatus()).isEqualTo(Status.UP);
		assertThat(health).isInstanceOf(Health.class);
	}

	@Test
	void healthWhenPathDoesNotExistReturnsHttp404() {
		this.registry.registerContributor("test", createContributor(this.up));
		WebEndpointResponse<? extends HealthComponent> response = create(this.registry, this.groups)
				.health(ApiVersion.LATEST, SecurityContext.NONE, "missing").block();
		assertThat(response.getBody()).isNull();
		assertThat(response.getStatus()).isEqualTo(404);
	}

	@Test
	void healthWhenPathExistsReturnsHealth() {
		this.registry.registerContributor("test", createContributor(this.up));
		WebEndpointResponse<? extends HealthComponent> response = create(this.registry, this.groups)
				.health(ApiVersion.LATEST, SecurityContext.NONE, "test").block();
		assertThat(response.getBody()).isEqualTo(this.up);
		assertThat(response.getStatus()).isEqualTo(200);
	}

	@Override
	protected ReactiveHealthEndpointWebExtension create(ReactiveHealthContributorRegistry registry,
			HealthEndpointGroups groups) {
		return new ReactiveHealthEndpointWebExtension(registry, groups);
	}

	@Override
	protected ReactiveHealthContributorRegistry createRegistry() {
		return new DefaultReactiveHealthContributorRegistry();
	}

	@Override
	protected ReactiveHealthContributor createContributor(Health health) {
		return (ReactiveHealthIndicator) () -> Mono.just(health);
	}

	@Override
	protected ReactiveHealthContributor createCompositeContributor(
			Map<String, ReactiveHealthContributor> contributors) {
		return CompositeReactiveHealthContributor.fromMap(contributors);
	}

	@Override
	protected HealthComponent getHealth(HealthResult<Mono<? extends HealthComponent>> result) {
		return result.getHealth().block();
	}

}
