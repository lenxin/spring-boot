package org.springframework.boot.buildpack.platform.docker.type;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/**
 * Tests for {@link RandomString}.
 *

 */
class RandomStringTests {

	@Test
	void generateWhenPrefixIsNullThrowsException() {
		assertThatIllegalArgumentException().isThrownBy(() -> RandomString.generate(null, 10))
				.withMessage("Prefix must not be null");
	}

	@Test
	void generateGeneratesRandomString() {
		String s1 = RandomString.generate("abc-", 10);
		String s2 = RandomString.generate("abc-", 10);
		String s3 = RandomString.generate("abc-", 20);
		assertThat(s1).hasSize(14).startsWith("abc-").isNotEqualTo(s2);
		assertThat(s3).hasSize(24).startsWith("abc-");
	}

}
