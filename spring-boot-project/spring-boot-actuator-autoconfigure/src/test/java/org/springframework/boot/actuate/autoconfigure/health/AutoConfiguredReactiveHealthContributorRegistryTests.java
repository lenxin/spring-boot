package org.springframework.boot.actuate.autoconfigure.health;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import org.springframework.boot.actuate.health.ReactiveHealthContributor;
import org.springframework.boot.actuate.health.ReactiveHealthContributorRegistry;

import static org.assertj.core.api.Assertions.assertThatIllegalStateException;
import static org.mockito.Mockito.mock;

/**
 * Tests for {@link AutoConfiguredReactiveHealthContributorRegistry}.
 *

 */
class AutoConfiguredReactiveHealthContributorRegistryTests {

	@Test
	void createWhenContributorsClashesWithGroupNameThrowsException() {
		assertThatIllegalStateException()
				.isThrownBy(() -> new AutoConfiguredReactiveHealthContributorRegistry(
						Collections.singletonMap("boot", mock(ReactiveHealthContributor.class)),
						Arrays.asList("spring", "boot")))
				.withMessage("ReactiveHealthContributor with name \"boot\" clashes with group");
	}

	@Test
	void registerContributorWithGroupNameThrowsException() {
		ReactiveHealthContributorRegistry registry = new AutoConfiguredReactiveHealthContributorRegistry(
				Collections.emptyMap(), Arrays.asList("spring", "boot"));
		assertThatIllegalStateException()
				.isThrownBy(() -> registry.registerContributor("spring", mock(ReactiveHealthContributor.class)))
				.withMessage("ReactiveHealthContributor with name \"spring\" clashes with group");
	}

}
