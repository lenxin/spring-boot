package org.springframework.boot.docs.test.system;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Sample showcasing the use of {@link CapturedOutput}.
 *

 */
// tag::test[]
@ExtendWith(OutputCaptureExtension.class)
class OutputCaptureTests {

	@Test
	void testName(CapturedOutput output) {
		System.out.println("Hello World!");
		assertThat(output).contains("World");
	}

}
// end::test[]
