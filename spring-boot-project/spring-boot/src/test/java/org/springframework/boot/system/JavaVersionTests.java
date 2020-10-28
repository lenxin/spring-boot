package org.springframework.boot.system;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnJre;
import org.junit.jupiter.api.condition.JRE;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link JavaVersion}.
 *

 */
class JavaVersionTests {

	@Test
	void getJavaVersionShouldBeAvailable() {
		assertThat(JavaVersion.getJavaVersion()).isNotNull();
	}

	@Test
	void compareToWhenComparingSmallerToGreaterShouldBeLessThanZero() {
		assertThat(JavaVersion.EIGHT.compareTo(JavaVersion.NINE)).isLessThan(0);
	}

	@Test
	void compareToWhenComparingGreaterToSmallerShouldBeGreaterThanZero() {
		assertThat(JavaVersion.NINE.compareTo(JavaVersion.EIGHT)).isGreaterThan(0);
	}

	@Test
	void compareToWhenComparingSameShouldBeZero() {
		assertThat(JavaVersion.EIGHT.compareTo(JavaVersion.EIGHT)).isEqualTo(0);
	}

	@Test
	void isEqualOrNewerThanWhenComparingSameShouldBeTrue() {
		assertThat(JavaVersion.EIGHT.isEqualOrNewerThan(JavaVersion.EIGHT)).isTrue();
	}

	@Test
	void isEqualOrNewerThanWhenSmallerToGreaterShouldBeFalse() {
		assertThat(JavaVersion.EIGHT.isEqualOrNewerThan(JavaVersion.NINE)).isFalse();
	}

	@Test
	void isEqualOrNewerThanWhenGreaterToSmallerShouldBeTrue() {
		assertThat(JavaVersion.NINE.isEqualOrNewerThan(JavaVersion.EIGHT)).isTrue();
	}

	@Test
	void isOlderThanThanWhenComparingSameShouldBeFalse() {
		assertThat(JavaVersion.EIGHT.isOlderThan(JavaVersion.EIGHT)).isFalse();
	}

	@Test
	void isOlderThanWhenSmallerToGreaterShouldBeTrue() {
		assertThat(JavaVersion.EIGHT.isOlderThan(JavaVersion.NINE)).isTrue();
	}

	@Test
	void isOlderThanWhenGreaterToSmallerShouldBeFalse() {
		assertThat(JavaVersion.NINE.isOlderThan(JavaVersion.EIGHT)).isFalse();
	}

	@Test
	@EnabledOnJre(JRE.JAVA_8)
	void currentJavaVersionEight() {
		assertThat(JavaVersion.getJavaVersion()).isEqualTo(JavaVersion.EIGHT);
	}

	@Test
	@EnabledOnJre(JRE.JAVA_9)
	void currentJavaVersionNine() {
		assertThat(JavaVersion.getJavaVersion()).isEqualTo(JavaVersion.NINE);
	}

	@Test
	@EnabledOnJre(JRE.JAVA_10)
	void currentJavaVersionTen() {
		assertThat(JavaVersion.getJavaVersion()).isEqualTo(JavaVersion.TEN);
	}

	@Test
	@EnabledOnJre(JRE.JAVA_11)
	void currentJavaVersionEleven() {
		assertThat(JavaVersion.getJavaVersion()).isEqualTo(JavaVersion.ELEVEN);
	}

	@Test
	@EnabledOnJre(JRE.JAVA_12)
	void currentJavaVersionTwelve() {
		assertThat(JavaVersion.getJavaVersion()).isEqualTo(JavaVersion.TWELVE);
	}

	@Test
	@EnabledOnJre(JRE.JAVA_13)
	void currentJavaVersionThirteen() {
		assertThat(JavaVersion.getJavaVersion()).isEqualTo(JavaVersion.THIRTEEN);
	}

	@Test
	@EnabledOnJre(JRE.JAVA_14)
	void currentJavaVersionFourteen() {
		assertThat(JavaVersion.getJavaVersion()).isEqualTo(JavaVersion.FOURTEEN);
	}

	@Test
	@EnabledOnJre(JRE.JAVA_15)
	void currentJavaVersionFifteen() {
		assertThat(JavaVersion.getJavaVersion()).isEqualTo(JavaVersion.FIFTEEN);
	}

}
