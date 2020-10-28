package org.springframework.boot.buildpack.platform.io;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link DefaultOwner}.
 *

 */
class DefaultOwnerTests {

	@Test
	void getUidReturnsUid() {
		DefaultOwner owner = new DefaultOwner(123, 456);
		assertThat(owner.getUid()).isEqualTo(123);
	}

	@Test
	void getGidReturnsGid() {
		DefaultOwner owner = new DefaultOwner(123, 456);
		assertThat(owner.getGid()).isEqualTo(456);
	}

	@Test
	void toStringReturnsString() {
		DefaultOwner owner = new DefaultOwner(123, 456);
		assertThat(owner).hasToString("123/456");
	}

}
