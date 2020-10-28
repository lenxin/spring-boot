package org.springframework.boot.gradle.docs;

import java.io.IOException;

import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.boot.gradle.junit.GradleMultiDslExtension;
import org.springframework.boot.gradle.testkit.GradleBuild;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for the publishing documentation.
 *


 */
@ExtendWith(GradleMultiDslExtension.class)
class PublishingDocumentationTests {

	GradleBuild gradleBuild;

	@TestTemplate
	void mavenUpload() throws IOException {
		assertThat(this.gradleBuild.expectDeprecationWarningsWithAtLeastVersion("5.6")
				.script("src/docs/gradle/publishing/maven").build("deployerRepository").getOutput())
						.contains("https://repo.example.com");
	}

	@TestTemplate
	void mavenPublish() throws IOException {
		assertThat(this.gradleBuild.script("src/docs/gradle/publishing/maven-publish").build("publishingConfiguration")
				.getOutput()).contains("MavenPublication").contains("https://repo.example.com");
	}

}
