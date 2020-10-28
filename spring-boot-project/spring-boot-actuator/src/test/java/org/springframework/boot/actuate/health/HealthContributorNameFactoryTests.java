package org.springframework.boot.actuate.health;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link HealthContributorNameFactory}.
 *

 */
class HealthContributorNameFactoryTests {

	@Test
	void applyWhenNameDoesNotEndWithSuffixReturnsName() {
		assertThat(HealthContributorNameFactory.INSTANCE.apply("test")).isEqualTo("test");
	}

	@Test
	void applyWhenNameEndsWithSuffixReturnsNewName() {
		assertThat(HealthContributorNameFactory.INSTANCE.apply("testHealthIndicator")).isEqualTo("test");
		assertThat(HealthContributorNameFactory.INSTANCE.apply("testHealthContributor")).isEqualTo("test");
	}

	@Test
	void applyWhenNameEndsWithSuffixInDifferentReturnsNewName() {
		assertThat(HealthContributorNameFactory.INSTANCE.apply("testHEALTHindicator")).isEqualTo("test");
		assertThat(HealthContributorNameFactory.INSTANCE.apply("testHEALTHcontributor")).isEqualTo("test");
	}

	@Test
	void applyWhenNameContainsSuffixReturnsName() {
		assertThat(HealthContributorNameFactory.INSTANCE.apply("testHealthIndicatorTest"))
				.isEqualTo("testHealthIndicatorTest");
		assertThat(HealthContributorNameFactory.INSTANCE.apply("testHealthContributorTest"))
				.isEqualTo("testHealthContributorTest");
	}

}
