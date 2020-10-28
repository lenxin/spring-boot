package org.springframework.boot.buildpack.platform.docker.type;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/**
 * Tests for {@link VolumeName}.
 *

 */
class VolumeNameTests {

	@Test
	void randomWhenPrefixIsNullThrowsException() {
		assertThatIllegalArgumentException().isThrownBy(() -> VolumeName.random(null))
				.withMessage("Prefix must not be null");
	}

	@Test
	void randomGeneratesRandomString() {
		VolumeName v1 = VolumeName.random("abc-");
		VolumeName v2 = VolumeName.random("abc-");
		assertThat(v1.toString()).startsWith("abc-").hasSize(14);
		assertThat(v2.toString()).startsWith("abc-").hasSize(14);
		assertThat(v1).isNotEqualTo(v2);
		assertThat(v1.toString()).isNotEqualTo(v2.toString());
	}

	@Test
	void randomStringWithLengthGeneratesRandomString() {
		VolumeName v1 = VolumeName.random("abc-", 20);
		VolumeName v2 = VolumeName.random("abc-", 20);
		assertThat(v1.toString()).startsWith("abc-").hasSize(24);
		assertThat(v2.toString()).startsWith("abc-").hasSize(24);
		assertThat(v1).isNotEqualTo(v2);
		assertThat(v1.toString()).isNotEqualTo(v2.toString());
	}

	@Test
	void basedOnWhenSourceIsNullThrowsException() {
		assertThatIllegalArgumentException().isThrownBy(() -> VolumeName.basedOn(null, "prefix", "suffix", 6))
				.withMessage("Source must not be null");
	}

	@Test
	void basedOnWhenNameExtractorIsNullThrowsException() {
		assertThatIllegalArgumentException().isThrownBy(() -> VolumeName.basedOn("test", null, "prefix", "suffix", 6))
				.withMessage("NameExtractor must not be null");
	}

	@Test
	void basedOnWhenPrefixIsNullThrowsException() {
		assertThatIllegalArgumentException().isThrownBy(() -> VolumeName.basedOn("test", null, "suffix", 6))
				.withMessage("Prefix must not be null");
	}

	@Test
	void basedOnWhenSuffixIsNullThrowsException() {
		assertThatIllegalArgumentException().isThrownBy(() -> VolumeName.basedOn("test", "prefix", null, 6))
				.withMessage("Suffix must not be null");
	}

	@Test
	void basedOnGeneratesHashBasedName() {
		VolumeName name = VolumeName.basedOn("index.docker.io/library/myapp:latest", "pack-cache-", ".build", 6);
		assertThat(name.toString()).isEqualTo("pack-cache-40a311b545d7.build");
	}

	@Test
	void basedOnWhenSizeIsTooBigThrowsException() {
		assertThatIllegalArgumentException().isThrownBy(() -> VolumeName.basedOn("name", "prefix", "suffix", 33))
				.withMessage("DigestLength must be less than or equal to 32");
	}

	@Test
	void ofWhenValueIsNullThrowsException() {
		assertThatIllegalArgumentException().isThrownBy(() -> VolumeName.of(null))
				.withMessage("Value must not be null");
	}

	@Test
	void ofGeneratesValue() {
		VolumeName name = VolumeName.of("test");
		assertThat(name.toString()).isEqualTo("test");
	}

	@Test
	void equalsAndHashCode() {
		VolumeName n1 = VolumeName.of("test1");
		VolumeName n2 = VolumeName.of("test1");
		VolumeName n3 = VolumeName.of("test2");
		assertThat(n1.hashCode()).isEqualTo(n2.hashCode());
		assertThat(n1).isEqualTo(n1).isEqualTo(n2).isNotEqualTo(n3);
	}

}
