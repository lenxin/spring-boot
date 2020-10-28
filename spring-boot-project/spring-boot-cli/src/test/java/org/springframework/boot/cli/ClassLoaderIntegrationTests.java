package org.springframework.boot.cli;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;

import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for CLI Classloader issues.
 *

 */
@ExtendWith(OutputCaptureExtension.class)
class ClassLoaderIntegrationTests {

	@RegisterExtension
	CliTester cli;

	ClassLoaderIntegrationTests(CapturedOutput output) {
		this.cli = new CliTester("src/test/resources/", output);
	}

	@Test
	void runWithIsolatedClassLoader() throws Exception {
		// CLI classes or dependencies should not be exposed to the app
		String output = this.cli.run("classloader-test-app.groovy", SpringCli.class.getName());
		assertThat(output).contains("HasClasses-false-true-false");
	}

}
