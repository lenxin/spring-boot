package org.springframework.boot.gradle.plugin;

import java.io.File;
import java.io.IOException;

import org.gradle.testkit.runner.BuildResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledForJreRange;
import org.junit.jupiter.api.condition.JRE;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.boot.gradle.testkit.GradleBuild;
import org.springframework.boot.gradle.testkit.GradleBuildExtension;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for {@link SpringBootPlugin}.
 *

 */
@ExtendWith(GradleBuildExtension.class)
class SpringBootPluginIntegrationTests {

	final GradleBuild gradleBuild = new GradleBuild();

	@DisabledForJreRange(min = JRE.JAVA_14)
	@Test
	void failFastWithVersionOfGradle5LowerThanRequired() {
		BuildResult result = this.gradleBuild.gradleVersion("5.5.1").buildAndFail();
		assertThat(result.getOutput())
				.contains("Spring Boot plugin requires Gradle 5 (5.6.x only) or Gradle 6 (6.3 or later). "
						+ "The current version is Gradle 5.5.1");
	}

	@DisabledForJreRange(min = JRE.JAVA_14)
	@Test
	void failFastWithVersionOfGradle6LowerThanRequired() {
		BuildResult result = this.gradleBuild.gradleVersion("6.2.2").buildAndFail();
		assertThat(result.getOutput())
				.contains("Spring Boot plugin requires Gradle 5 (5.6.x only) or Gradle 6 (6.3 or later). "
						+ "The current version is Gradle 6.2.2");
	}

	@DisabledForJreRange(min = JRE.JAVA_13)
	@Test
	void succeedWithVersionOfGradle5HigherThanRequired() {
		this.gradleBuild.gradleVersion("5.6.1").build();
	}

	@DisabledForJreRange(min = JRE.JAVA_13)
	@Test
	void succeedWithVersionOfGradle5MatchingWhatIsRequired() {
		this.gradleBuild.gradleVersion("5.6").build();
	}

	@Test
	void succeedWithVersionOfGradle6MatchingWithIsRequired() {
		this.gradleBuild.gradleVersion("6.3").build();
	}

	private void createMinimalMainSource() throws IOException {
		File examplePackage = new File(this.gradleBuild.getProjectDir(), "src/main/java/com/example");
		examplePackage.mkdirs();
		new File(examplePackage, "Application.java").createNewFile();
	}

}
