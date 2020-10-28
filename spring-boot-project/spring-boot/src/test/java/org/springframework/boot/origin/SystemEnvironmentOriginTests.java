package org.springframework.boot.origin;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/**
 * Tests for {@link SystemEnvironmentOrigin}.
 *

 */
class SystemEnvironmentOriginTests {

	@Test
	void createWhenPropertyIsNullShouldThrowException() {
		assertThatIllegalArgumentException().isThrownBy(() -> new SystemEnvironmentOrigin(null));
	}

	@Test
	void createWhenPropertyNameIsEmptyShouldThrowException() {
		assertThatIllegalArgumentException().isThrownBy(() -> new SystemEnvironmentOrigin(""));
	}

	@Test
	void getPropertyShouldReturnProperty() {
		SystemEnvironmentOrigin origin = new SystemEnvironmentOrigin("FOO_BAR");
		assertThat(origin.getProperty()).isEqualTo("FOO_BAR");
	}

	@Test
	void toStringShouldReturnStringWithDetails() {
		SystemEnvironmentOrigin origin = new SystemEnvironmentOrigin("FOO_BAR");
		assertThat(origin.toString()).isEqualTo("System Environment Property \"FOO_BAR\"");
	}

}
