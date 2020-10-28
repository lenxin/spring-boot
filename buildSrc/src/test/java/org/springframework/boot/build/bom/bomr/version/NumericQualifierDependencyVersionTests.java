package org.springframework.boot.build.bom.bomr.version;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link NumericQualifierDependencyVersion}.
 *

 */
public class NumericQualifierDependencyVersionTests {

	@Test
	void isNewerThanOnVersionWithNumericQualifierWhenInputHasNoQualifierShouldReturnTrue() {
		assertThat(version("2.9.9.20190806").isNewerThan(DependencyVersion.parse("2.9.9"))).isTrue();
	}

	@Test
	void isNewerThanOnVersionWithNumericQualifierWhenInputHasOlderQualifierShouldReturnTrue() {
		assertThat(version("2.9.9.20190806").isNewerThan(version("2.9.9.20190805"))).isTrue();
	}

	@Test
	void isNewerThanOnVersionWithNumericQualifierWhenInputHasNewerQualifierShouldReturnFalse() {
		assertThat(version("2.9.9.20190806").isNewerThan(version("2.9.9.20190807"))).isFalse();
	}

	@Test
	void isNewerThanOnVersionWithNumericQualifierWhenInputHasSameQualifierShouldReturnFalse() {
		assertThat(version("2.9.9.20190806").isNewerThan(version("2.9.9.20190806"))).isFalse();
	}

	private NumericQualifierDependencyVersion version(String version) {
		return NumericQualifierDependencyVersion.parse(version);
	}

}
