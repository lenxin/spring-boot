package org.springframework.boot.cli;

import java.util.concurrent.ExecutionException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;

import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

/**
 * Integration tests to exercise and reproduce specific issues.
 *



 */
@ExtendWith(OutputCaptureExtension.class)
class ReproIntegrationTests {

	@RegisterExtension
	CliTester cli;

	ReproIntegrationTests(CapturedOutput output) {
		this.cli = new CliTester("src/test/resources/repro-samples/", output);
	}

	@Test
	void grabAntBuilder() throws Exception {
		this.cli.run("grab-ant-builder.groovy");
		assertThat(this.cli.getHttpOutput()).contains("{\"message\":\"Hello World\"}");
	}

	// Security depends on old versions of Spring so if the dependencies aren't pinned
	// this will fail
	@Test
	void securityDependencies() throws Exception {
		assertThat(this.cli.run("secure.groovy")).contains("Hello World");
	}

	@Test
	void dataJpaDependencies() throws Exception {
		assertThat(this.cli.run("data-jpa.groovy")).contains("Hello World");
	}

	@Test
	void jarFileExtensionNeeded() throws Exception {
		assertThatExceptionOfType(ExecutionException.class)
				.isThrownBy(() -> this.cli.jar("secure.groovy", "data-jpa.groovy"))
				.withMessageContaining("is not a JAR file");
	}

}
