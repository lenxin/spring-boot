package org.springframework.boot.actuate.health;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/**
 * Tests for {@link NamedContributor}.
 *

 */
class NamedContributorTests {

	@Test
	void ofNameAndContributorCreatesContributor() {
		NamedContributor<String> contributor = NamedContributor.of("one", "two");
		assertThat(contributor.getName()).isEqualTo("one");
		assertThat(contributor.getContributor()).isEqualTo("two");
	}

	@Test
	void ofWhenNameIsNullThrowsException() {
		assertThatIllegalArgumentException().isThrownBy(() -> NamedContributor.of(null, "two"))
				.withMessage("Name must not be null");
	}

	@Test
	void ofWhenContributorIsNullThrowsException() {
		assertThatIllegalArgumentException().isThrownBy(() -> NamedContributor.of("one", null))
				.withMessage("Contributor must not be null");

	}

}
