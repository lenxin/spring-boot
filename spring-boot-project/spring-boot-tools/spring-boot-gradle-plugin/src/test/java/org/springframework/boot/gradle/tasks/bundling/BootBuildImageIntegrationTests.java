package org.springframework.boot.gradle.tasks.bundling;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

import org.gradle.testkit.runner.BuildResult;
import org.gradle.testkit.runner.TaskOutcome;
import org.junit.jupiter.api.TestTemplate;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;

import org.springframework.boot.buildpack.platform.docker.DockerApi;
import org.springframework.boot.buildpack.platform.docker.type.ImageName;
import org.springframework.boot.buildpack.platform.docker.type.ImageReference;
import org.springframework.boot.gradle.junit.GradleCompatibility;
import org.springframework.boot.gradle.testkit.GradleBuild;
import org.springframework.boot.testsupport.testcontainers.DisabledIfDockerUnavailable;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for {@link BootBuildImage}.
 *


 */
@GradleCompatibility(configurationCache = true)
@DisabledIfDockerUnavailable
class BootBuildImageIntegrationTests {

	GradleBuild gradleBuild;

	@TestTemplate
	void buildsImageWithDefaultBuilder() throws IOException {
		writeMainClass();
		writeLongNameResource();
		BuildResult result = this.gradleBuild.build("bootBuildImage");
		String projectName = this.gradleBuild.getProjectDir().getName();
		assertThat(result.task(":bootBuildImage").getOutcome()).isEqualTo(TaskOutcome.SUCCESS);
		assertThat(result.getOutput()).contains("docker.io/library/" + projectName);
		assertThat(result.getOutput()).contains("paketobuildpacks/builder");
		ImageReference imageReference = ImageReference.of(ImageName.of(projectName));
		try (GenericContainer<?> container = new GenericContainer<>(imageReference.toString())) {
			container.waitingFor(Wait.forLogMessage("Launched\\n", 1)).start();
		}
		finally {
			new DockerApi().image().remove(imageReference, false);
		}
	}

	@TestTemplate
	void buildsImageWithCustomName() throws IOException {
		writeMainClass();
		writeLongNameResource();
		BuildResult result = this.gradleBuild.build("bootBuildImage");
		assertThat(result.task(":bootBuildImage").getOutcome()).isEqualTo(TaskOutcome.SUCCESS);
		assertThat(result.getOutput()).contains("example/test-image-name");
		assertThat(result.getOutput()).contains("paketobuildpacks/builder");
		ImageReference imageReference = ImageReference.of(ImageName.of("example/test-image-name"));
		try (GenericContainer<?> container = new GenericContainer<>(imageReference.toString())) {
			container.waitingFor(Wait.forLogMessage("Launched\\n", 1)).start();
		}
		finally {
			new DockerApi().image().remove(imageReference, false);
		}
	}

	@TestTemplate
	void buildsImageWithCustomBuilderAndRunImage() throws IOException {
		writeMainClass();
		writeLongNameResource();
		BuildResult result = this.gradleBuild.build("bootBuildImage");
		assertThat(result.task(":bootBuildImage").getOutcome()).isEqualTo(TaskOutcome.SUCCESS);
		assertThat(result.getOutput()).contains("example/test-image-custom");
		assertThat(result.getOutput()).contains("paketobuildpacks/builder:full");
		assertThat(result.getOutput()).contains("paketobuildpacks/run:full");
		ImageReference imageReference = ImageReference.of(ImageName.of("example/test-image-custom"));
		try (GenericContainer<?> container = new GenericContainer<>(imageReference.toString())) {
			container.waitingFor(Wait.forLogMessage("Launched\\n", 1)).start();
		}
		finally {
			new DockerApi().image().remove(imageReference, false);
		}
	}

	@TestTemplate
	void buildsImageWithCommandLineOptions() throws IOException {
		writeMainClass();
		writeLongNameResource();
		BuildResult result = this.gradleBuild.build("bootBuildImage", "--imageName=example/test-image-cmd",
				"--builder=paketobuildpacks/builder:full", "--runImage=paketobuildpacks/run:full-cnb");
		assertThat(result.task(":bootBuildImage").getOutcome()).isEqualTo(TaskOutcome.SUCCESS);
		assertThat(result.getOutput()).contains("example/test-image-cmd");
		assertThat(result.getOutput()).contains("paketobuildpacks/builder:full");
		assertThat(result.getOutput()).contains("paketobuildpacks/run:full");
		ImageReference imageReference = ImageReference.of(ImageName.of("example/test-image-cmd"));
		try (GenericContainer<?> container = new GenericContainer<>(imageReference.toString())) {
			container.waitingFor(Wait.forLogMessage("Launched\\n", 1)).start();
		}
		finally {
			new DockerApi().image().remove(imageReference, false);
		}
	}

	@TestTemplate
	void buildsImageWithPullPolicy() throws IOException {
		writeMainClass();
		writeLongNameResource();
		String projectName = this.gradleBuild.getProjectDir().getName();
		ImageReference imageReference = ImageReference.of(ImageName.of(projectName));

		BuildResult result = this.gradleBuild.build("bootBuildImage", "--pullPolicy=ALWAYS");
		assertThat(result.task(":bootBuildImage").getOutcome()).isEqualTo(TaskOutcome.SUCCESS);
		assertThat(result.getOutput()).contains("Pulled builder image").contains("Pulled run image");
		try (GenericContainer<?> container = new GenericContainer<>(imageReference.toString())) {
			container.waitingFor(Wait.forLogMessage("Launched\\n", 1)).start();
		}

		result = this.gradleBuild.build("bootBuildImage", "--pullPolicy=IF_NOT_PRESENT");
		assertThat(result.task(":bootBuildImage").getOutcome()).isEqualTo(TaskOutcome.SUCCESS);
		assertThat(result.getOutput()).doesNotContain("Pulled builder image").doesNotContain("Pulled run image");
		try (GenericContainer<?> container = new GenericContainer<>(imageReference.toString())) {
			container.waitingFor(Wait.forLogMessage("Launched\\n", 1)).start();
		}
		finally {
			new DockerApi().image().remove(imageReference, false);
		}
	}

	@TestTemplate
	void failsWithLaunchScript() {
		writeMainClass();
		writeLongNameResource();
		BuildResult result = this.gradleBuild.buildAndFail("bootBuildImage");
		assertThat(result.task(":bootBuildImage").getOutcome()).isEqualTo(TaskOutcome.FAILED);
		assertThat(result.getOutput()).contains("not compatible with buildpacks");
	}

	@TestTemplate
	void failsWithBuilderError() {
		writeMainClass();
		writeLongNameResource();
		BuildResult result = this.gradleBuild.buildAndFail("bootBuildImage");
		assertThat(result.task(":bootBuildImage").getOutcome()).isEqualTo(TaskOutcome.FAILED);
		assertThat(result.getOutput()).containsPattern("Builder lifecycle '.*' failed with status code");
	}

	@TestTemplate
	void failsWithInvalidImageName() {
		writeMainClass();
		writeLongNameResource();
		BuildResult result = this.gradleBuild.buildAndFail("bootBuildImage", "--imageName=example/Invalid-Image-Name");
		assertThat(result.task(":bootBuildImage").getOutcome()).isEqualTo(TaskOutcome.FAILED);
		assertThat(result.getOutput()).containsPattern("Unable to parse image reference")
				.containsPattern("example/Invalid-Image-Name");
	}

	@TestTemplate
	void failsWithPublishMissingPublishRegistry() {
		writeMainClass();
		writeLongNameResource();
		BuildResult result = this.gradleBuild.buildAndFail("bootBuildImage", "--publishImage");
		assertThat(result.task(":bootBuildImage").getOutcome()).isEqualTo(TaskOutcome.FAILED);
		assertThat(result.getOutput()).contains("requires docker.publishRegistry");
	}

	private void writeMainClass() {
		File examplePackage = new File(this.gradleBuild.getProjectDir(), "src/main/java/example");
		examplePackage.mkdirs();
		File main = new File(examplePackage, "Main.java");
		try (PrintWriter writer = new PrintWriter(new FileWriter(main))) {
			writer.println("package example;");
			writer.println();
			writer.println("import java.io.IOException;");
			writer.println();
			writer.println("public class Main {");
			writer.println();
			writer.println("    public static void main(String[] args) throws Exception {");
			writer.println("        System.out.println(\"Launched\");");
			writer.println("        synchronized(args) {");
			writer.println("            args.wait(); // Prevent exit");
			writer.println("        }");
			writer.println("    }");
			writer.println();
			writer.println("}");
		}
		catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}

	private void writeLongNameResource() {
		StringBuilder name = new StringBuilder();
		new Random().ints('a', 'z' + 1).limit(128).forEach((i) -> name.append((char) i));
		try {
			Path path = this.gradleBuild.getProjectDir().toPath()
					.resolve(Paths.get("src", "main", "resources", name.toString()));
			Files.createDirectories(path.getParent());
			Files.createFile(path);
		}
		catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}

}
