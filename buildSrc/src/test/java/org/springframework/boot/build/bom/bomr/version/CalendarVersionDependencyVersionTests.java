package org.springframework.boot.build.bom.bomr.version;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link CalendarVersionDependencyVersion}.
 *

 */
public class CalendarVersionDependencyVersionTests {

	@Test
	void parseWhenVersionIsNotACalendarVersionShouldReturnNull() {
		assertThat(version("1.2.3")).isNull();
	}

	@Test
	void parseWhenVersionIsACalendarVersionShouldReturnAVersion() {
		assertThat(version("2020.0.0")).isNotNull();
	}

	@Test
	void isNewerThanWhenInputIsEarlierYearShouldReturnTrue() {
		assertThat(version("2020.1.2").isNewerThan(version("2019.9.0"))).isTrue();
	}

	@Test
	void isNewerThanWhenInputIsOlderMinorShouldReturnTrue() {
		assertThat(version("2020.1.2").isNewerThan(version("2020.0.2"))).isTrue();
	}

	@Test
	void isNewerThanWhenInputIsOlderMicroShouldReturnTrue() {
		assertThat(version("2020.1.2").isNewerThan(version("2020.1.1"))).isTrue();
	}

	@Test
	void isNewerThanWhenInputIsLaterYearShouldReturnFalse() {
		assertThat(version("2020.1.2").isNewerThan(version("2021.2.1"))).isFalse();
	}

	@Test
	void isSameMajorAndNewerThanWhenMinorIsOlderShouldReturnTrue() {
		assertThat(version("2020.10.2").isSameMajorAndNewerThan(version("2020.9.0"))).isTrue();
	}

	@Test
	void isSameMajorAndNewerThanWhenMajorIsOlderShouldReturnFalse() {
		assertThat(version("2020.0.2").isSameMajorAndNewerThan(version("2019.9.0"))).isFalse();
	}

	@Test
	void isSameMajorAndNewerThanWhenMicroIsNewerShouldReturnTrue() {
		assertThat(version("2020.1.2").isSameMajorAndNewerThan(version("2020.1.1"))).isTrue();
	}

	@Test
	void isSameMajorAndNewerThanWhenMinorIsNewerShouldReturnFalse() {
		assertThat(version("2020.1.2").isSameMajorAndNewerThan(version("2020.2.1"))).isFalse();
	}

	@Test
	void isSameMajorAndNewerThanWhenMajorIsNewerShouldReturnFalse() {
		assertThat(version("2019.1.2").isSameMajorAndNewerThan(version("2020.0.1"))).isFalse();
	}

	@Test
	void isSameMinorAndNewerThanWhenMicroIsOlderShouldReturnTrue() {
		assertThat(version("2020.10.2").isSameMinorAndNewerThan(version("2020.10.1"))).isTrue();
	}

	@Test
	void isSameMinorAndNewerThanWhenMinorIsOlderShouldReturnFalse() {
		assertThat(version("2020.1.2").isSameMinorAndNewerThan(version("2020.0.1"))).isFalse();
	}

	@Test
	void isSameMinorAndNewerThanWhenVersionsAreTheSameShouldReturnFalse() {
		assertThat(version("2020.1.2").isSameMinorAndNewerThan(version("2020.1.2"))).isFalse();
	}

	@Test
	void isSameMinorAndNewerThanWhenMicroIsNewerShouldReturnFalse() {
		assertThat(version("2020.1.2").isSameMinorAndNewerThan(version("2020.1.3"))).isFalse();
	}

	@Test
	void isSameMinorAndNewerThanWhenMinorIsNewerShouldReturnFalse() {
		assertThat(version("2020.1.2").isSameMinorAndNewerThan(version("2020.0.1"))).isFalse();
	}

	@Test
	void isSameMinorAndNewerThanWhenMajorIsNewerShouldReturnFalse() {
		assertThat(version("2020.1.2").isSameMinorAndNewerThan(version("2019.0.1"))).isFalse();
	}

	@Test
	void calendarVersionIsNewerThanReleaseTrainVersion() {
		assertThat(version("2020.0.0").isNewerThan(releaseTrainVersion("Aluminium-RELEASE"))).isTrue();
	}

	@Test
	void calendarVersionIsNotSameMajorAsReleaseTrainVersion() {
		assertThat(version("2020.0.0").isSameMajorAndNewerThan(releaseTrainVersion("Aluminium-RELEASE"))).isFalse();
	}

	@Test
	void calendarVersionIsNotSameMinorAsReleaseTrainVersion() {
		assertThat(version("2020.0.0").isSameMinorAndNewerThan(releaseTrainVersion("Aluminium-RELEASE"))).isFalse();
	}

	private ReleaseTrainDependencyVersion releaseTrainVersion(String version) {
		return ReleaseTrainDependencyVersion.parse(version);
	}

	private CalendarVersionDependencyVersion version(String version) {
		return CalendarVersionDependencyVersion.parse(version);
	}

}
