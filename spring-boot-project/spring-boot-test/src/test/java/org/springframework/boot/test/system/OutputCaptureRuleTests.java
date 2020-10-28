package org.springframework.boot.test.system;

import org.junit.Rule;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link OutputCaptureRule}.
 *

 */
public class OutputCaptureRuleTests {

	@Rule
	public OutputCaptureRule output = new OutputCaptureRule();

	@Test
	public void toStringShouldReturnAllCapturedOutput() {
		System.out.println("Hello World");
		assertThat(this.output.toString()).contains("Hello World");
	}

	@Test
	public void captureShouldBeAssertable() {
		System.out.println("Hello World");
		assertThat(this.output).contains("Hello World");
	}

}
