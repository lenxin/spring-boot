package org.springframework.boot.buildpack.platform.build;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;

/**
 * Tests for {@link ApiVersion}.
 *


 */
class ApiVersionTests {

	@Test
	void parseWhenVersionIsNullThrowsException() {
		assertThatIllegalArgumentException().isThrownBy(() -> ApiVersion.parse(null))
				.withMessage("Value must not be empty");
	}

	@Test
	void parseWhenVersionIsEmptyThrowsException() {
		assertThatIllegalArgumentException().isThrownBy(() -> ApiVersion.parse(""))
				.withMessage("Value must not be empty");
	}

	@Test
	void parseWhenVersionDoesNotMatchPatternThrowsException() {
		assertThatIllegalArgumentException().isThrownBy(() -> ApiVersion.parse("bad"))
				.withMessage("Malformed version number 'bad'");
	}

	@Test
	void parseReturnsVersion() {
		ApiVersion version = ApiVersion.parse("1.2");
		assertThat(version.getMajor()).isEqualTo(1);
		assertThat(version.getMinor()).isEqualTo(2);
	}

	@Test
	void assertSupportsWhenSupports() {
		ApiVersion.parse("1.2").assertSupports(ApiVersion.parse("1.0"));
	}

	@Test
	void assertSupportsWhenDoesNotSupportThrowsException() {
		assertThatIllegalStateException()
				.isThrownBy(() -> ApiVersion.parse("1.2").assertSupports(ApiVersion.parse("1.3")))
				.withMessage("Detected platform API version '1.3' does not match supported version '1.2'");
	}

	@Test
	void supportWhenSame() {
		assertThat(supports("0.0", "0.0")).isTrue();
		assertThat(supports("0.1", "0.1")).isTrue();
		assertThat(supports("1.0", "1.0")).isTrue();
		assertThat(supports("1.1", "1.1")).isTrue();
	}

	@Test
	void supportsWhenDifferentMajor() {
		assertThat(supports("0.0", "1.0")).isFalse();
		assertThat(supports("1.0", "0.0")).isFalse();
		assertThat(supports("1.0", "2.0")).isFalse();
		assertThat(supports("2.0", "1.0")).isFalse();
		assertThat(supports("1.1", "2.1")).isFalse();
		assertThat(supports("2.1", "1.1")).isFalse();
	}

	@Test
	void supportsWhenDifferentMinor() {
		assertThat(supports("1.2", "1.1")).isTrue();
		assertThat(supports("1.2", "1.3")).isFalse();
	}

	@Test
	void supportWhenMajorZeroAndDifferentMinor() {
		assertThat(supports("0.2", "0.1")).isFalse();
		assertThat(supports("0.2", "0.3")).isFalse();
	}

	@Test
	void toStringReturnsString() {
		assertThat(ApiVersion.parse("1.2").toString()).isEqualTo("1.2");
	}

	@Test
	void equalsAndHashCode() {
		ApiVersion v12a = ApiVersion.parse("1.2");
		ApiVersion v12b = ApiVersion.parse("1.2");
		ApiVersion v13 = ApiVersion.parse("1.3");
		assertThat(v12a.hashCode()).isEqualTo(v12b.hashCode());
		assertThat(v12a).isEqualTo(v12a).isEqualTo(v12b).isNotEqualTo(v13);
	}

	private boolean supports(String v1, String v2) {
		return ApiVersion.parse(v1).supports(ApiVersion.parse(v2));
	}

}
