package org.springframework.boot.buildpack.platform.io;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link Owner}.
 *

 */
class OwnerTests {

	@Test
	void ofReturnsNewOwner() {
		Owner owner = Owner.of(123, 456);
		assertThat(owner.getUid()).isEqualTo(123);
		assertThat(owner.getGid()).isEqualTo(456);
	}

}
