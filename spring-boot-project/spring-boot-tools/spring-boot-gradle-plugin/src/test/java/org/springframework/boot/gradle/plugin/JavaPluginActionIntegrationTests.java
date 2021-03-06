package org.springframework.boot.gradle.plugin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.jar.JarOutputStream;

import org.gradle.testkit.runner.BuildResult;
import org.gradle.testkit.runner.TaskOutcome;
import org.junit.jupiter.api.TestTemplate;

import org.springframework.boot.gradle.junit.GradleCompatibility;
import org.springframework.boot.gradle.testkit.GradleBuild;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for {@link WarPluginAction}.
 *

 */
@GradleCompatibility
class JavaPluginActionIntegrationTests {

	GradleBuild gradleBuild;

	@TestTemplate
	void noBootJarTaskWithoutJavaPluginApplied() {
		assertThat(this.gradleBuild.build("taskExists", "-PtaskName=bootJar").getOutput())
				.contains("bootJar exists = false");
	}

	@TestTemplate
	void applyingJavaPluginCreatesBootJarTask() {
		assertThat(this.gradleBuild.build("taskExists", "-PtaskName=bootJar", "-PapplyJavaPlugin").getOutput())
				.contains("bootJar exists = true");
	}

	@TestTemplate
	void noBootRunTaskWithoutJavaPluginApplied() {
		assertThat(this.gradleBuild.build("taskExists", "-PtaskName=bootRun").getOutput())
				.contains("bootRun exists = false");
	}

	@TestTemplate
	void applyingJavaPluginCreatesBootRunTask() {
		assertThat(this.gradleBuild.build("taskExists", "-PtaskName=bootRun", "-PapplyJavaPlugin").getOutput())
				.contains("bootRun exists = true");
	}

	@TestTemplate
	void javaCompileTasksUseUtf8Encoding() {
		assertThat(this.gradleBuild.build("javaCompileEncoding", "-PapplyJavaPlugin").getOutput())
				.contains("compileJava = UTF-8").contains("compileTestJava = UTF-8");
	}

	@TestTemplate
	void javaCompileTasksUseParametersCompilerFlagByDefault() {
		assertThat(this.gradleBuild.build("javaCompileTasksCompilerArgs").getOutput())
				.contains("compileJava compiler args: [-parameters]")
				.contains("compileTestJava compiler args: [-parameters]");
	}

	@TestTemplate
	void javaCompileTasksUseParametersAndAdditionalCompilerFlags() {
		assertThat(this.gradleBuild.build("javaCompileTasksCompilerArgs").getOutput())
				.contains("compileJava compiler args: [-parameters, -Xlint:all]")
				.contains("compileTestJava compiler args: [-parameters, -Xlint:all]");
	}

	@TestTemplate
	void javaCompileTasksCanOverrideDefaultParametersCompilerFlag() {
		assertThat(this.gradleBuild.build("javaCompileTasksCompilerArgs").getOutput())
				.contains("compileJava compiler args: [-Xlint:all]")
				.contains("compileTestJava compiler args: [-Xlint:all]");
	}

	@TestTemplate
	void assembleRunsBootJarAndJarIsSkipped() {
		BuildResult result = this.gradleBuild.build("assemble");
		assertThat(result.task(":bootJar").getOutcome()).isEqualTo(TaskOutcome.SUCCESS);
		assertThat(result.task(":jar").getOutcome()).isEqualTo(TaskOutcome.SKIPPED);
	}

	@TestTemplate
	void errorMessageIsHelpfulWhenMainClassCannotBeResolved() {
		BuildResult result = this.gradleBuild.buildAndFail("build", "-PapplyJavaPlugin");
		assertThat(result.task(":bootJar").getOutcome()).isEqualTo(TaskOutcome.FAILED);
		assertThat(result.getOutput()).contains("Main class name has not been configured and it could not be resolved");
	}

	@TestTemplate
	void jarAndBootJarCanBothBeBuilt() {
		BuildResult result = this.gradleBuild.build("assemble");
		assertThat(result.task(":bootJar").getOutcome()).isEqualTo(TaskOutcome.SUCCESS);
		assertThat(result.task(":jar").getOutcome()).isEqualTo(TaskOutcome.SUCCESS);
		File buildLibs = new File(this.gradleBuild.getProjectDir(), "build/libs");
		assertThat(buildLibs.listFiles()).containsExactlyInAnyOrder(
				new File(buildLibs, this.gradleBuild.getProjectDir().getName() + ".jar"),
				new File(buildLibs, this.gradleBuild.getProjectDir().getName() + "-boot.jar"));
	}

	@TestTemplate
	void additionalMetadataLocationsConfiguredWhenProcessorIsPresent() throws IOException {
		createMinimalMainSource();
		File libs = new File(this.gradleBuild.getProjectDir(), "libs");
		libs.mkdirs();
		new JarOutputStream(new FileOutputStream(new File(libs, "spring-boot-configuration-processor-1.2.3.jar")))
				.close();
		BuildResult result = this.gradleBuild.build("compileJava");
		assertThat(result.task(":compileJava").getOutcome()).isEqualTo(TaskOutcome.SUCCESS);
		assertThat(result.getOutput()).contains("compileJava compiler args: [-parameters, -Aorg.springframework.boot."
				+ "configurationprocessor.additionalMetadataLocations="
				+ new File(this.gradleBuild.getProjectDir(), "src/main/resources").getCanonicalPath());
	}

	@TestTemplate
	void additionalMetadataLocationsNotConfiguredWhenProcessorIsAbsent() throws IOException {
		createMinimalMainSource();
		BuildResult result = this.gradleBuild.build("compileJava");
		assertThat(result.task(":compileJava").getOutcome()).isEqualTo(TaskOutcome.SUCCESS);
		assertThat(result.getOutput()).contains("compileJava compiler args: [-parameters]");
	}

	@TestTemplate
	void applyingJavaPluginCreatesDevelopmentOnlyConfiguration() {
		assertThat(this.gradleBuild
				.build("configurationExists", "-PconfigurationName=developmentOnly", "-PapplyJavaPlugin").getOutput())
						.contains("developmentOnly exists = true");
	}

	@TestTemplate
	void productionRuntimeClasspathIsConfiguredWithAttributes() {
		assertThat(this.gradleBuild
				.build("configurationAttributes", "-PconfigurationName=productionRuntimeClasspath", "-PapplyJavaPlugin")
				.getOutput()).contains("3 productionRuntimeClasspath attributes:")
						.contains("org.gradle.usage: java-runtime").contains("org.gradle.libraryelements: jar")
						.contains("org.gradle.dependency.bundling: external");
	}

	private void createMinimalMainSource() throws IOException {
		File examplePackage = new File(this.gradleBuild.getProjectDir(), "src/main/java/com/example");
		examplePackage.mkdirs();
		new File(examplePackage, "Application.java").createNewFile();
	}

}
