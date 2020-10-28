package org.springframework.boot.cli;

import java.io.File;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;

import org.springframework.boot.cli.command.grab.GrabCommand;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.util.FileSystemUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

/**
 * Integration tests for {@link GrabCommand}
 *


 */
@ExtendWith(OutputCaptureExtension.class)
class GrabCommandIntegrationTests {

	@RegisterExtension
	CliTester cli;

	GrabCommandIntegrationTests(CapturedOutput output) {
		this.cli = new CliTester("src/test/resources/grab-samples/", output);
	}

	@BeforeEach
	@AfterEach
	void deleteLocalRepository() {
		System.clearProperty("grape.root");
		System.clearProperty("groovy.grape.report.downloads");
	}

	@Test
	void grab() throws Exception {
		System.setProperty("grape.root", this.cli.getTemp().getAbsolutePath());
		System.setProperty("groovy.grape.report.downloads", "true");
		// Use --autoconfigure=false to limit the amount of downloaded dependencies
		String output = this.cli.grab("grab.groovy", "--autoconfigure=false");
		assertThat(new File(this.cli.getTemp(), "repository/com/fasterxml/jackson/core/jackson-core")).isDirectory();
		assertThat(output).contains("Downloading: ");
	}

	@Test
	void duplicateDependencyManagementBomAnnotationsProducesAnError() {
		assertThatExceptionOfType(Exception.class)
				.isThrownBy(() -> this.cli.grab("duplicateDependencyManagementBom.groovy"))
				.withMessageContaining("Duplicate @DependencyManagementBom annotation");
	}

	@Test
	void customMetadata() throws Exception {
		System.setProperty("grape.root", this.cli.getTemp().getAbsolutePath());
		File repository = new File(this.cli.getTemp().getAbsolutePath(), "repository");
		FileSystemUtils.copyRecursively(new File("src/test/resources/grab-samples/repository"), repository);
		this.cli.grab("customDependencyManagement.groovy", "--autoconfigure=false");
		assertThat(new File(repository, "javax/ejb/ejb-api/3.0")).isDirectory();
	}

}
