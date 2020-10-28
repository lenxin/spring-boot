package org.springframework.boot.ansi;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/**
 * Tests for {@link Ansi8BitColor}.
 *


 */
class Ansi8BitColorTests {

	@Test
	void toStringWhenForegroundAddsCorrectPrefix() {
		assertThat(Ansi8BitColor.foreground(208).toString()).isEqualTo("38;5;208");
	}

	@Test
	void toStringWhenBackgroundAddsCorrectPrefix() {
		assertThat(Ansi8BitColor.background(208).toString()).isEqualTo("48;5;208");
	}

	@Test
	void foregroundWhenOutsideBoundsThrowsException() {
		assertThatIllegalArgumentException().isThrownBy(() -> Ansi8BitColor.foreground(-1))
				.withMessage("Code must be between 0 and 255");
		assertThatIllegalArgumentException().isThrownBy(() -> Ansi8BitColor.foreground(256))
				.withMessage("Code must be between 0 and 255");
	}

	@Test
	void backgroundWhenOutsideBoundsThrowsException() {
		assertThatIllegalArgumentException().isThrownBy(() -> Ansi8BitColor.background(-1))
				.withMessage("Code must be between 0 and 255");
		assertThatIllegalArgumentException().isThrownBy(() -> Ansi8BitColor.background(256))
				.withMessage("Code must be between 0 and 255");
	}

	@Test
	void equalsAndHashCode() {
		Ansi8BitColor one = Ansi8BitColor.foreground(123);
		Ansi8BitColor two = Ansi8BitColor.foreground(123);
		Ansi8BitColor three = Ansi8BitColor.background(123);
		assertThat(one.hashCode()).isEqualTo(two.hashCode());
		assertThat(one).isEqualTo(one).isEqualTo(two).isNotEqualTo(three).isNotEqualTo(null).isNotEqualTo("foo");
	}

}
