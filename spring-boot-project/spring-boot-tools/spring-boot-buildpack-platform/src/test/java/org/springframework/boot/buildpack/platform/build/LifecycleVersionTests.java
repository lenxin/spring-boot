package org.springframework.boot.buildpack.platform.build;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/**
 * Tests for {@link LifecycleVersion}.
 *

 */
class LifecycleVersionTests {

	@Test
	void parseWhenValueIsNullThrowsException() {
		assertThatIllegalArgumentException().isThrownBy(() -> LifecycleVersion.parse(null))
				.withMessage("Value must not be empty");
	}

	@Test
	void parseWhenTooLongThrowsException() {
		assertThatIllegalArgumentException().isThrownBy(() -> LifecycleVersion.parse("v1.2.3.4"))
				.withMessage("Malformed version number '1.2.3.4'");
	}

	@Test
	void parseWhenNonNumericThrowsException() {
		assertThatIllegalArgumentException().isThrownBy(() -> LifecycleVersion.parse("v1.2.3a"))
				.withMessage("Malformed version number '1.2.3a'");
	}

	@Test
	void compareTo() {
		LifecycleVersion v4 = LifecycleVersion.parse("0.0.4");
		assertThat(LifecycleVersion.parse("0.0.3").compareTo(v4)).isNegative();
		assertThat(LifecycleVersion.parse("0.0.4").compareTo(v4)).isZero();
		assertThat(LifecycleVersion.parse("0.0.5").compareTo(v4)).isPositive();
	}

	@Test
	void isEqualOrGreaterThan() {
		LifecycleVersion v4 = LifecycleVersion.parse("0.0.4");
		assertThat(LifecycleVersion.parse("0.0.3").isEqualOrGreaterThan(v4)).isFalse();
		assertThat(LifecycleVersion.parse("0.0.4").isEqualOrGreaterThan(v4)).isTrue();
		assertThat(LifecycleVersion.parse("0.0.5").isEqualOrGreaterThan(v4)).isTrue();
	}

	@Test
	void parseReturnsVersion() {
		assertThat(LifecycleVersion.parse("1.2.3").toString()).isEqualTo("v1.2.3");
		assertThat(LifecycleVersion.parse("1.2").toString()).isEqualTo("v1.2.0");
		assertThat(LifecycleVersion.parse("1").toString()).isEqualTo("v1.0.0");
	}

}
