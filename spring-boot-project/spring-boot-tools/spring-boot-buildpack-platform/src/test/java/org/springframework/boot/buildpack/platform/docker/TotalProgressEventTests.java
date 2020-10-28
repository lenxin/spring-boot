package org.springframework.boot.buildpack.platform.docker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/**
 * Tests for {@link TotalProgressEvent}.
 *

 */
class TotalProgressEventTests {

	@Test
	void create() {
		assertThat(new TotalProgressEvent(0).getPercent()).isEqualTo(0);
		assertThat(new TotalProgressEvent(10).getPercent()).isEqualTo(10);
		assertThat(new TotalProgressEvent(100).getPercent()).isEqualTo(100);
	}

	@Test
	void createWhenPercentLessThanZeroThrowsException() {
		assertThatIllegalArgumentException().isThrownBy(() -> new TotalProgressEvent(-1))
				.withMessage("Percent must be in the range 0 to 100");
	}

	@Test
	void createWhenEventMoreThanOneHundredThrowsException() {
		assertThatIllegalArgumentException().isThrownBy(() -> new TotalProgressEvent(101))
				.withMessage("Percent must be in the range 0 to 100");
	}

}
