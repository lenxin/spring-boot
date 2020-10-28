package org.springframework.boot.actuate.health;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link CompositeReactiveHealthContributor}.
 *

 */
class CompositeReactiveHealthContributorTests {

	@Test
	void fromMapReturnsCompositeReactiveHealthContributorMapAdapter() {
		Map<String, ReactiveHealthContributor> map = new LinkedHashMap<>();
		ReactiveHealthIndicator indicator = () -> Mono.just(Health.down().build());
		map.put("test", indicator);
		CompositeReactiveHealthContributor composite = CompositeReactiveHealthContributor.fromMap(map);
		assertThat(composite).isInstanceOf(CompositeReactiveHealthContributorMapAdapter.class);
		NamedContributor<ReactiveHealthContributor> namedContributor = composite.iterator().next();
		assertThat(namedContributor.getName()).isEqualTo("test");
		assertThat(namedContributor.getContributor()).isSameAs(indicator);
	}

	@Test
	void fromMapWithAdapterReturnsCompositeReactiveHealthContributorMapAdapter() {
		Map<String, ReactiveHealthContributor> map = new LinkedHashMap<>();
		ReactiveHealthIndicator downIndicator = () -> Mono.just(Health.down().build());
		ReactiveHealthIndicator upIndicator = () -> Mono.just(Health.up().build());
		map.put("test", downIndicator);
		CompositeReactiveHealthContributor composite = CompositeReactiveHealthContributor.fromMap(map,
				(value) -> upIndicator);
		assertThat(composite).isInstanceOf(CompositeReactiveHealthContributorMapAdapter.class);
		NamedContributor<ReactiveHealthContributor> namedContributor = composite.iterator().next();
		assertThat(namedContributor.getName()).isEqualTo("test");
		assertThat(namedContributor.getContributor()).isSameAs(upIndicator);
	}

}
