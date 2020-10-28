package org.springframework.boot.cli;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;

import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for code in directories.
 *

 */
@ExtendWith(OutputCaptureExtension.class)
class DirectorySourcesIntegrationTests {

	@RegisterExtension
	CliTester cli;

	DirectorySourcesIntegrationTests(CapturedOutput output) {
		this.cli = new CliTester("src/test/resources/dir-sample/", output);
	}

	@Test
	void runDirectory() throws Exception {
		assertThat(this.cli.run("code")).contains("Hello World");
	}

	@Test
	void runDirectoryRecursive() throws Exception {
		assertThat(this.cli.run("")).contains("Hello World");
	}

	@Test
	void runPathPattern() throws Exception {
		assertThat(this.cli.run("**/*.groovy")).contains("Hello World");
	}

}
