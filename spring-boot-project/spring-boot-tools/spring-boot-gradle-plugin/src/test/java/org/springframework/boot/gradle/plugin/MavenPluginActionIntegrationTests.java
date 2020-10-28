package org.springframework.boot.gradle.plugin;

import org.junit.jupiter.api.TestTemplate;

import org.springframework.boot.gradle.junit.GradleCompatibility;
import org.springframework.boot.gradle.testkit.GradleBuild;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for {@link MavenPluginAction}.
 *

 */
@GradleCompatibility
class MavenPluginActionIntegrationTests {

	GradleBuild gradleBuild;

	@TestTemplate
	void clearsConf2ScopeMappingsOfUploadBootArchivesTask() {
		assertThat(this.gradleBuild.expectDeprecationWarningsWithAtLeastVersion("6.0.0").build("conf2ScopeMappings")
				.getOutput()).contains("Conf2ScopeMappings = 0");
	}

}
