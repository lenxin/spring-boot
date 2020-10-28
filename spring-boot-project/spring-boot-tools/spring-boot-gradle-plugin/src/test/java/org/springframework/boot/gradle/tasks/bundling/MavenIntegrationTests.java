package org.springframework.boot.gradle.tasks.bundling;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.gradle.testkit.runner.BuildResult;
import org.gradle.testkit.runner.TaskOutcome;
import org.junit.jupiter.api.TestTemplate;

import org.springframework.boot.gradle.junit.GradleCompatibility;
import org.springframework.boot.gradle.testkit.GradleBuild;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for uploading Boot jars and wars using Gradle's Maven plugin.
 *

 */
@GradleCompatibility
class MavenIntegrationTests {

	GradleBuild gradleBuild;

	@TestTemplate
	void bootJarCanBeUploaded() throws FileNotFoundException, IOException {
		BuildResult result = this.gradleBuild.expectDeprecationWarningsWithAtLeastVersion("6.0.0")
				.build("uploadBootArchives");
		assertThat(result.task(":uploadBootArchives").getOutcome()).isEqualTo(TaskOutcome.SUCCESS);
		assertThat(artifactWithSuffix("jar")).isFile();
		assertThat(artifactWithSuffix("pom")).is(pomWith().groupId("com.example")
				.artifactId(this.gradleBuild.getProjectDir().getName()).version("1.0").noPackaging().noDependencies());
	}

	@TestTemplate
	void bootWarCanBeUploaded() throws IOException {
		BuildResult result = this.gradleBuild.expectDeprecationWarningsWithAtLeastVersion("6.0.0")
				.build("uploadBootArchives");
		assertThat(result.task(":uploadBootArchives").getOutcome()).isEqualTo(TaskOutcome.SUCCESS);
		assertThat(artifactWithSuffix("war")).isFile();
		assertThat(artifactWithSuffix("pom"))
				.is(pomWith().groupId("com.example").artifactId(this.gradleBuild.getProjectDir().getName())
						.version("1.0").packaging("war").noDependencies());
	}

	private File artifactWithSuffix(String suffix) {
		String name = this.gradleBuild.getProjectDir().getName();
		return new File(new File(this.gradleBuild.getProjectDir(), "build/repo"),
				String.format("com/example/%s/1.0/%s-1.0.%s", name, name, suffix));
	}

	private PomCondition pomWith() {
		return new PomCondition();
	}

}
