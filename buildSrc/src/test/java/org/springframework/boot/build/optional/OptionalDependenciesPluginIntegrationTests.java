package org.springframework.boot.build.optional;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.gradle.testkit.runner.BuildResult;
import org.gradle.testkit.runner.GradleRunner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for {@link OptionalDependenciesPlugin}.
 *

 */
public class OptionalDependenciesPluginIntegrationTests {

	private File projectDir;

	private File buildFile;

	@BeforeEach
	public void setup(@TempDir File projectDir) throws IOException {
		this.projectDir = projectDir;
		this.buildFile = new File(this.projectDir, "build.gradle");
	}

	@Test
	void optionalConfigurationIsCreated() throws IOException {
		try (PrintWriter out = new PrintWriter(new FileWriter(this.buildFile))) {
			out.println("plugins { id 'org.springframework.boot.optional-dependencies' }");
			out.println("task printConfigurations {");
			out.println("    doLast {");
			out.println("        configurations.all { println it.name }");
			out.println("    }");
			out.println("}");
		}
		BuildResult buildResult = runGradle("printConfigurations");
		assertThat(buildResult.getOutput()).contains(OptionalDependenciesPlugin.OPTIONAL_CONFIGURATION_NAME);
	}

	@Test
	void optionalDependenciesAreAddedToMainSourceSetsCompileClasspath() throws IOException {
		optionalDependenciesAreAddedToSourceSetClasspath("main", "compileClasspath");
	}

	@Test
	void optionalDependenciesAreAddedToMainSourceSetsRuntimeClasspath() throws IOException {
		optionalDependenciesAreAddedToSourceSetClasspath("main", "runtimeClasspath");
	}

	@Test
	void optionalDependenciesAreAddedToTestSourceSetsCompileClasspath() throws IOException {
		optionalDependenciesAreAddedToSourceSetClasspath("test", "compileClasspath");
	}

	@Test
	void optionalDependenciesAreAddedToTestSourceSetsRuntimeClasspath() throws IOException {
		optionalDependenciesAreAddedToSourceSetClasspath("test", "runtimeClasspath");
	}

	public void optionalDependenciesAreAddedToSourceSetClasspath(String sourceSet, String classpath)
			throws IOException {
		try (PrintWriter out = new PrintWriter(new FileWriter(this.buildFile))) {
			out.println("plugins {");
			out.println("    id 'org.springframework.boot.optional-dependencies'");
			out.println("    id 'java'");
			out.println("}");
			out.println("repositories {");
			out.println("    mavenCentral()");
			out.println("}");
			out.println("dependencies {");
			out.println("    optional 'org.springframework:spring-jcl:5.1.2.RELEASE'");
			out.println("}");
			out.println("task printClasspath {");
			out.println("    doLast {");
			out.println("        println sourceSets." + sourceSet + "." + classpath + ".files");
			out.println("    }");
			out.println("}");
		}
		BuildResult buildResult = runGradle("printClasspath");
		assertThat(buildResult.getOutput()).contains("spring-jcl");
	}

	private BuildResult runGradle(String... args) {
		return GradleRunner.create().withProjectDir(this.projectDir).withArguments(args).withPluginClasspath().build();
	}

}
