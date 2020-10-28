package org.springframework.boot.buildpack.platform.build;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link BuilderException}.
 *

 */
class BuilderExceptionTests {

	@Test
	void create() {
		BuilderException exception = new BuilderException("detector", 1);
		assertThat(exception.getOperation()).isEqualTo("detector");
		assertThat(exception.getStatusCode()).isEqualTo(1);
		assertThat(exception.getMessage()).isEqualTo("Builder lifecycle 'detector' failed with status code 1");
	}

	@Test
	void createWhenOperationIsNull() {
		BuilderException exception = new BuilderException(null, 1);
		assertThat(exception.getOperation()).isNull();
		assertThat(exception.getStatusCode()).isEqualTo(1);
		assertThat(exception.getMessage()).isEqualTo("Builder failed with status code 1");
	}

}
