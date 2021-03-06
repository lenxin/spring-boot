package org.springframework.boot.build;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.gradle.testkit.runner.BuildResult;
import org.gradle.testkit.runner.GradleRunner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import org.springframework.util.FileCopyUtils;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for {@link ConventionsPlugin}.
 *

 */
class ConventionsPluginTests {

	private File projectDir;

	private File buildFile;

	@BeforeEach
	void setup(@TempDir File projectDir) throws IOException {
		this.projectDir = projectDir;
		this.buildFile = new File(this.projectDir, "build.gradle");
		File settingsFile = new File(this.projectDir, "settings.gradle");
		try (PrintWriter out = new PrintWriter(new FileWriter(settingsFile))) {
			out.println("include ':spring-boot-project:spring-boot-parent'");
		}
		File springBootParent = new File(this.projectDir, "spring-boot-project/spring-boot-parent/build.gradle");
		springBootParent.getParentFile().mkdirs();
		try (PrintWriter out = new PrintWriter(new FileWriter(springBootParent))) {
			out.println("plugins {");
			out.println("    id 'java-platform'");
			out.println("}");
		}
	}

	@Test
	void jarIncludesLegalFiles() throws IOException {
		try (PrintWriter out = new PrintWriter(new FileWriter(this.buildFile))) {
			out.println("plugins {");
			out.println("    id 'java'");
			out.println("    id 'org.springframework.boot.conventions'");
			out.println("}");
			out.println("description 'Test'");
			out.println("jar.archiveFileName = 'test.jar'");
		}
		runGradle("jar");
		File file = new File(this.projectDir, "/build/libs/test.jar");
		assertThat(file).exists();
		try (JarFile jar = new JarFile(file)) {
			JarEntry license = jar.getJarEntry("META-INF/LICENSE.txt");
			assertThat(license).isNotNull();
			JarEntry notice = jar.getJarEntry("META-INF/NOTICE.txt");
			assertThat(notice).isNotNull();
			String noticeContent = FileCopyUtils.copyToString(new InputStreamReader(jar.getInputStream(notice)));
			// Test that variables were replaced
			assertThat(noticeContent).doesNotContain("${");
		}
	}

	@Test
	void testRetryIsConfiguredWithThreeRetriesOnCI() throws IOException {
		try (PrintWriter out = new PrintWriter(new FileWriter(this.buildFile))) {
			out.println("plugins {");
			out.println("    id 'java'");
			out.println("    id 'org.springframework.boot.conventions'");
			out.println("}");
			out.println("description 'Test'");
			out.println("task retryConfig {");
			out.println("    doLast {");
			out.println("        println \"Retry plugin applied: ${plugins.hasPlugin('org.gradle.test-retry')}\"");
			out.println("    test.retry {");
			out.println("            println \"maxRetries: ${maxRetries.get()}\"");
			out.println("            println \"failOnPassedAfterRetry: ${failOnPassedAfterRetry.get()}\"");
			out.println("        }");
			out.println("    }");
			out.println("}");
		}
		assertThat(runGradle(Collections.singletonMap("CI", "true"), "retryConfig", "--stacktrace").getOutput())
				.contains("Retry plugin applied: true").contains("maxRetries: 3")
				.contains("failOnPassedAfterRetry: true");
	}

	@Test
	void testRetryIsConfiguredWithZeroRetriesLocally() throws IOException {
		try (PrintWriter out = new PrintWriter(new FileWriter(this.buildFile))) {
			out.println("plugins {");
			out.println("    id 'java'");
			out.println("    id 'org.springframework.boot.conventions'");
			out.println("}");
			out.println("description 'Test'");
			out.println("task retryConfig {");
			out.println("    doLast {");
			out.println("        println \"Retry plugin applied: ${plugins.hasPlugin('org.gradle.test-retry')}\"");
			out.println("    test.retry {");
			out.println("            println \"maxRetries: ${maxRetries.get()}\"");
			out.println("            println \"failOnPassedAfterRetry: ${failOnPassedAfterRetry.get()}\"");
			out.println("        }");
			out.println("    }");
			out.println("}");
		}
		assertThat(runGradle(Collections.singletonMap("CI", "local"), "retryConfig", "--stacktrace").getOutput())
				.contains("Retry plugin applied: true").contains("maxRetries: 0")
				.contains("failOnPassedAfterRetry: true");
	}

	private BuildResult runGradle(String... args) {
		return runGradle(Collections.emptyMap(), args);
	}

	private BuildResult runGradle(Map<String, String> environment, String... args) {
		return GradleRunner.create().withProjectDir(this.projectDir).withEnvironment(environment).withArguments(args)
				.withPluginClasspath().build();
	}

}
