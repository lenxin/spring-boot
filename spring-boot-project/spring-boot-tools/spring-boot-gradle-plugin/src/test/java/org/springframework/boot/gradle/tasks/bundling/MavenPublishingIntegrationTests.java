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
 * Integration tests for publishing Boot jars and wars using Gradle's Maven Publish
 * plugin.
 *

 */
@GradleCompatibility
class MavenPublishingIntegrationTests {

	GradleBuild gradleBuild;

	@TestTemplate
	void bootJarCanBePublished() throws FileNotFoundException, IOException {
		BuildResult result = this.gradleBuild.build("publish");
		assertThat(result.task(":publish").getOutcome()).isEqualTo(TaskOutcome.SUCCESS);
		assertThat(artifactWithSuffix("jar")).isFile();
		assertThat(artifactWithSuffix("pom")).is(pomWith().groupId("com.example")
				.artifactId(this.gradleBuild.getProjectDir().getName()).version("1.0").noPackaging().noDependencies());
	}

	@TestTemplate
	void bootWarCanBePublished() throws IOException {
		BuildResult result = this.gradleBuild.build("publish");
		assertThat(result.task(":publish").getOutcome()).isEqualTo(TaskOutcome.SUCCESS);
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
