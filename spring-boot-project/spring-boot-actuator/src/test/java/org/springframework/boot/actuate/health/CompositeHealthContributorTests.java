package org.springframework.boot.actuate.health;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link CompositeHealthContributor}.
 *

 */
class CompositeHealthContributorTests {

	@Test
	void fromMapReturnsCompositeHealthContributorMapAdapter() {
		Map<String, HealthContributor> map = new LinkedHashMap<>();
		HealthIndicator indicator = () -> Health.down().build();
		map.put("test", indicator);
		CompositeHealthContributor composite = CompositeHealthContributor.fromMap(map);
		assertThat(composite).isInstanceOf(CompositeHealthContributorMapAdapter.class);
		NamedContributor<HealthContributor> namedContributor = composite.iterator().next();
		assertThat(namedContributor.getName()).isEqualTo("test");
		assertThat(namedContributor.getContributor()).isSameAs(indicator);
	}

	@Test
	void fromMapWithAdapterReturnsCompositeHealthContributorMapAdapter() {
		Map<String, HealthContributor> map = new LinkedHashMap<>();
		HealthIndicator downIndicator = () -> Health.down().build();
		HealthIndicator upIndicator = () -> Health.up().build();
		map.put("test", downIndicator);
		CompositeHealthContributor composite = CompositeHealthContributor.fromMap(map, (value) -> upIndicator);
		assertThat(composite).isInstanceOf(CompositeHealthContributorMapAdapter.class);
		NamedContributor<HealthContributor> namedContributor = composite.iterator().next();
		assertThat(namedContributor.getName()).isEqualTo("test");
		assertThat(namedContributor.getContributor()).isSameAs(upIndicator);
	}

}
