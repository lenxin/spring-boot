package org.springframework.boot.autoconfigure.h2;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/**
 * Tests for {@link H2ConsoleProperties}.
 *

 */
class H2ConsolePropertiesTests {

	@Test
	void pathMustNotBeEmpty() {
		H2ConsoleProperties properties = new H2ConsoleProperties();
		assertThatIllegalArgumentException().isThrownBy(() -> properties.setPath(""))
				.withMessageContaining("Path must have length greater than 1");
	}

	@Test
	void pathMustHaveLengthGreaterThanOne() {
		H2ConsoleProperties properties = new H2ConsoleProperties();
		assertThatIllegalArgumentException().isThrownBy(() -> properties.setPath("/"))
				.withMessageContaining("Path must have length greater than 1");
	}

	@Test
	void customPathMustBeginWithASlash() {
		H2ConsoleProperties properties = new H2ConsoleProperties();
		assertThatIllegalArgumentException().isThrownBy(() -> properties.setPath("custom"))
				.withMessageContaining("Path must start with '/'");
	}

}
