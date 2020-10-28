package org.springframework.boot.buildpack.platform.docker.type;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/**
 * Tests for {@link ContainerReference}.
 *

 */
class ContainerReferenceTests {

	@Test
	void ofCreatesInstance() {
		ContainerReference reference = ContainerReference
				.of("92691aec176333f7ae890de9aaeeafef11166efcaa3908edf83eb44a5c943781");
		assertThat(reference.toString()).isEqualTo("92691aec176333f7ae890de9aaeeafef11166efcaa3908edf83eb44a5c943781");
	}

	@Test
	void ofWhenNullThrowsException() {
		assertThatIllegalArgumentException().isThrownBy(() -> ContainerReference.of(null))
				.withMessage("Value must not be empty");
	}

	@Test
	void ofWhenEmptyThrowsException() {
		assertThatIllegalArgumentException().isThrownBy(() -> ContainerReference.of(""))
				.withMessage("Value must not be empty");
	}

	@Test
	void hashCodeAndEquals() {
		ContainerReference r1 = ContainerReference
				.of("92691aec176333f7ae890de9aaeeafef11166efcaa3908edf83eb44a5c943781");
		ContainerReference r2 = ContainerReference
				.of("92691aec176333f7ae890de9aaeeafef11166efcaa3908edf83eb44a5c943781");
		ContainerReference r3 = ContainerReference
				.of("02691aec176333f7ae890de9aaeeafef11166efcaa3908edf83eb44a5c943781");
		assertThat(r1.hashCode()).isEqualTo(r2.hashCode());
		assertThat(r1).isEqualTo(r1).isEqualTo(r2).isNotEqualTo(r3);
	}

}
