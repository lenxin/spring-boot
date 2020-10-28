package org.springframework.boot.actuate.autoconfigure.cloudfoundry;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link AccessLevel}.
 *

 */
class AccessLevelTests {

	@Test
	void accessToHealthEndpointShouldNotBeRestricted() {
		assertThat(AccessLevel.RESTRICTED.isAccessAllowed("health")).isTrue();
		assertThat(AccessLevel.FULL.isAccessAllowed("health")).isTrue();
	}

	@Test
	void accessToInfoEndpointShouldNotBeRestricted() {
		assertThat(AccessLevel.RESTRICTED.isAccessAllowed("info")).isTrue();
		assertThat(AccessLevel.FULL.isAccessAllowed("info")).isTrue();
	}

	@Test
	void accessToDiscoveryEndpointShouldNotBeRestricted() {
		assertThat(AccessLevel.RESTRICTED.isAccessAllowed("")).isTrue();
		assertThat(AccessLevel.FULL.isAccessAllowed("")).isTrue();
	}

	@Test
	void accessToAnyOtherEndpointShouldBeRestricted() {
		assertThat(AccessLevel.RESTRICTED.isAccessAllowed("env")).isFalse();
		assertThat(AccessLevel.FULL.isAccessAllowed("")).isTrue();
	}

}
