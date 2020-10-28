package org.springframework.boot.loader.tools.layer;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/**
 * Tests for {@link ApplicationContentFilter}.
 *



 */
class ApplicationContentFilterTests {

	@Test
	void createWhenPatternIsNullThrowsException() {
		assertThatIllegalArgumentException().isThrownBy(() -> new ApplicationContentFilter(null))
				.withMessage("Pattern must not be empty");
	}

	@Test
	void createWhenPatternIsEmptyThrowsException() {
		assertThatIllegalArgumentException().isThrownBy(() -> new ApplicationContentFilter(""))
				.withMessage("Pattern must not be empty");
	}

	@Test
	void matchesWhenWildcardPatternMatchesReturnsTrue() {
		ApplicationContentFilter filter = new ApplicationContentFilter("META-INF/**");
		assertThat(filter.matches("META-INF/resources/application.yml")).isTrue();
	}

	@Test
	void matchesWhenWildcardPatternDoesNotMatchReturnsFalse() {
		ApplicationContentFilter filter = new ApplicationContentFilter("META-INF/**");
		assertThat(filter.matches("src/main/resources/application.yml")).isFalse();
	}

}
