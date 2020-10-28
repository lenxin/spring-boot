package org.springframework.boot.actuate.autoconfigure.health;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import org.springframework.boot.actuate.health.HealthContributor;
import org.springframework.boot.actuate.health.HealthContributorRegistry;

import static org.assertj.core.api.Assertions.assertThatIllegalStateException;
import static org.mockito.Mockito.mock;

/**
 * Tests for {@link AutoConfiguredHealthContributorRegistry}.
 *

 */
class AutoConfiguredHealthContributorRegistryTests {

	@Test
	void createWhenContributorsClashesWithGroupNameThrowsException() {
		assertThatIllegalStateException()
				.isThrownBy(() -> new AutoConfiguredHealthContributorRegistry(
						Collections.singletonMap("boot", mock(HealthContributor.class)),
						Arrays.asList("spring", "boot")))
				.withMessage("HealthContributor with name \"boot\" clashes with group");
	}

	@Test
	void registerContributorWithGroupNameThrowsException() {
		HealthContributorRegistry registry = new AutoConfiguredHealthContributorRegistry(Collections.emptyMap(),
				Arrays.asList("spring", "boot"));
		assertThatIllegalStateException()
				.isThrownBy(() -> registry.registerContributor("spring", mock(HealthContributor.class)))
				.withMessage("HealthContributor with name \"spring\" clashes with group");
	}

}
