package org.springframework.boot.test.system;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link OutputCaptureExtension} when used via {@link ExtendWith @ExtendWith}.
 *

 */
@ExtendWith(OutputCaptureExtension.class)
@ExtendWith(OutputExtensionExtendWithTests.BeforeAllExtension.class)
class OutputExtensionExtendWithTests {

	@Test
	void captureShouldReturnOutputCapturedBeforeTestMethod(CapturedOutput output) {
		assertThat(output).contains("Before all").doesNotContain("Hello");
	}

	@Test
	void captureShouldReturnAllCapturedOutput(CapturedOutput output) {
		System.out.println("Hello World");
		System.err.println("Error!!!");
		assertThat(output).contains("Before all").contains("Hello World").contains("Error!!!");
	}

	static class BeforeAllExtension implements BeforeAllCallback {

		@Override
		public void beforeAll(ExtensionContext context) {
			System.out.println("Before all");
		}

	}

}
