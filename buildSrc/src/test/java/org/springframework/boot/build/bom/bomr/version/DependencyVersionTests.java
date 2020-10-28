package org.springframework.boot.build.bom.bomr.version;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link DependencyVersion}.
 *

 */
public class DependencyVersionTests {

	@Test
	void parseWhenValidMavenVersionShouldReturnArtifactVersionDependencyVersion() {
		assertThat(DependencyVersion.parse("1.2.3.Final")).isExactlyInstanceOf(ArtifactVersionDependencyVersion.class);
	}

	@Test
	void parseWhenReleaseTrainShouldReturnReleaseTrainDependencyVersion() {
		assertThat(DependencyVersion.parse("Ingalls-SR5")).isInstanceOf(ReleaseTrainDependencyVersion.class);
	}

	@Test
	void parseWhenMavenLikeVersionWithNumericQualifierShouldReturnNumericQualifierDependencyVersion() {
		assertThat(DependencyVersion.parse("1.2.3.4")).isInstanceOf(NumericQualifierDependencyVersion.class);
	}

	@Test
	void parseWhenVersionWithLeadingZeroesShouldReturnLeadingZeroesDependencyVersion() {
		assertThat(DependencyVersion.parse("1.4.01")).isInstanceOf(LeadingZeroesDependencyVersion.class);
	}

	@Test
	void parseWhenVersionWithCombinedPatchAndQualifierShouldReturnCombinedPatchAndQualifierDependencyVersion() {
		assertThat(DependencyVersion.parse("4.0.0M4")).isInstanceOf(CombinedPatchAndQualifierDependencyVersion.class);
	}

	@Test
	void parseWhenCalendarVersionShouldReturnArticatVersionDependencyVersion() {
		assertThat(DependencyVersion.parse("2020.0.0")).isInstanceOf(CalendarVersionDependencyVersion.class);
	}

	@Test
	void parseWhenCalendarVersionWithModifierShouldReturnArticatVersionDependencyVersion() {
		assertThat(DependencyVersion.parse("2020.0.0-M1")).isInstanceOf(CalendarVersionDependencyVersion.class);
	}

	@Test
	void calendarVersionShouldBeNewerThanAReleaseCalendarVersion() {

	}

}
