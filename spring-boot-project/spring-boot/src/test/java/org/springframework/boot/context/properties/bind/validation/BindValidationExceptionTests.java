package org.springframework.boot.context.properties.bind.validation;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.mockito.Mockito.mock;

/**
 * Tests for {@link BindValidationException}.
 *


 */
class BindValidationExceptionTests {

	@Test
	void createWhenValidationErrorsIsNullShouldThrowException() {
		assertThatIllegalArgumentException().isThrownBy(() -> new BindValidationException(null))
				.withMessageContaining("ValidationErrors must not be null");
	}

	@Test
	void getValidationErrorsShouldReturnValidationErrors() {
		ValidationErrors errors = mock(ValidationErrors.class);
		BindValidationException exception = new BindValidationException(errors);
		assertThat(exception.getValidationErrors()).isEqualTo(errors);
	}

}
