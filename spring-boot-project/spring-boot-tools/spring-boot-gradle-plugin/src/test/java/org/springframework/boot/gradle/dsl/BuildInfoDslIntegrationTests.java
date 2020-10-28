package org.springframework.boot.gradle.dsl;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.gradle.testkit.runner.TaskOutcome;
import org.junit.jupiter.api.TestTemplate;

import org.springframework.boot.gradle.junit.GradleCompatibility;
import org.springframework.boot.gradle.tasks.buildinfo.BuildInfo;
import org.springframework.boot.gradle.testkit.GradleBuild;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for {@link BuildInfo} created using the
 * {@link org.springframework.boot.gradle.dsl.SpringBootExtension DSL}.
 *

 */
@GradleCompatibility
class BuildInfoDslIntegrationTests {

	GradleBuild gradleBuild;

	@TestTemplate
	void basicJar() throws IOException {
		assertThat(this.gradleBuild.build("bootBuildInfo", "--stacktrace").task(":bootBuildInfo").getOutcome())
				.isEqualTo(TaskOutcome.SUCCESS);
		Properties properties = buildInfoProperties();
		assertThat(properties).containsEntry("build.name", this.gradleBuild.getProjectDir().getName());
		assertThat(properties).containsEntry("build.artifact", this.gradleBuild.getProjectDir().getName());
		assertThat(properties).containsEntry("build.group", "com.example");
		assertThat(properties).containsEntry("build.version", "1.0");
	}

	@TestTemplate
	void jarWithCustomName() throws IOException {
		assertThat(this.gradleBuild.build("bootBuildInfo", "--stacktrace").task(":bootBuildInfo").getOutcome())
				.isEqualTo(TaskOutcome.SUCCESS);
		Properties properties = buildInfoProperties();
		assertThat(properties).containsEntry("build.name", this.gradleBuild.getProjectDir().getName());
		assertThat(properties).containsEntry("build.artifact", "foo");
		assertThat(properties).containsEntry("build.group", "com.example");
		assertThat(properties).containsEntry("build.version", "1.0");
	}

	@TestTemplate
	void basicWar() throws IOException {
		assertThat(this.gradleBuild.build("bootBuildInfo", "--stacktrace").task(":bootBuildInfo").getOutcome())
				.isEqualTo(TaskOutcome.SUCCESS);
		Properties properties = buildInfoProperties();
		assertThat(properties).containsEntry("build.name", this.gradleBuild.getProjectDir().getName());
		assertThat(properties).containsEntry("build.artifact", this.gradleBuild.getProjectDir().getName());
		assertThat(properties).containsEntry("build.group", "com.example");
		assertThat(properties).containsEntry("build.version", "1.0");
	}

	@TestTemplate
	void warWithCustomName() throws IOException {
		assertThat(this.gradleBuild.build("bootBuildInfo", "--stacktrace").task(":bootBuildInfo").getOutcome())
				.isEqualTo(TaskOutcome.SUCCESS);
		Properties properties = buildInfoProperties();
		assertThat(properties).containsEntry("build.name", this.gradleBuild.getProjectDir().getName());
		assertThat(properties).containsEntry("build.artifact", "foo");
		assertThat(properties).containsEntry("build.group", "com.example");
		assertThat(properties).containsEntry("build.version", "1.0");
	}

	@TestTemplate
	void additionalProperties() throws IOException {
		assertThat(this.gradleBuild.build("bootBuildInfo", "--stacktrace").task(":bootBuildInfo").getOutcome())
				.isEqualTo(TaskOutcome.SUCCESS);
		Properties properties = buildInfoProperties();
		assertThat(properties).containsEntry("build.name", this.gradleBuild.getProjectDir().getName());
		assertThat(properties).containsEntry("build.artifact", this.gradleBuild.getProjectDir().getName());
		assertThat(properties).containsEntry("build.group", "com.example");
		assertThat(properties).containsEntry("build.version", "1.0");
		assertThat(properties).containsEntry("build.a", "alpha");
		assertThat(properties).containsEntry("build.b", "bravo");
	}

	@TestTemplate
	void classesDependency() throws IOException {
		assertThat(this.gradleBuild.build("classes", "--stacktrace").task(":bootBuildInfo").getOutcome())
				.isEqualTo(TaskOutcome.SUCCESS);
	}

	private Properties buildInfoProperties() {
		File file = new File(this.gradleBuild.getProjectDir(), "build/resources/main/META-INF/build-info.properties");
		assertThat(file).isFile();
		Properties properties = new Properties();
		try (FileReader reader = new FileReader(file)) {
			properties.load(reader);
			return properties;
		}
		catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}

}
