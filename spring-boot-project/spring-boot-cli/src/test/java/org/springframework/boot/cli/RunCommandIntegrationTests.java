package org.springframework.boot.cli;

import java.util.Properties;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;

import org.springframework.boot.cli.command.run.RunCommand;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for {@link RunCommand}.
 *

 */
@ExtendWith(OutputCaptureExtension.class)
class RunCommandIntegrationTests {

	@RegisterExtension
	CliTester cli;

	RunCommandIntegrationTests(CapturedOutput output) {
		this.cli = new CliTester("src/test/resources/run-command/", output);
	}

	private Properties systemProperties = new Properties();

	@BeforeEach
	void captureSystemProperties() {
		this.systemProperties.putAll(System.getProperties());
	}

	@AfterEach
	void restoreSystemProperties() {
		System.setProperties(this.systemProperties);
	}

	@Test
	void bannerAndLoggingIsOutputByDefault() throws Exception {
		String output = this.cli.run("quiet.groovy");
		assertThat(output).contains(" :: Spring Boot ::");
		assertThat(output).contains("Starting application");
		assertThat(output).contains("Ssshh");
	}

	@Test
	void quietModeSuppressesAllCliOutput() throws Exception {
		this.cli.run("quiet.groovy");
		String output = this.cli.run("quiet.groovy", "-q");
		assertThat(output).isEqualTo("Ssshh");
	}

}
