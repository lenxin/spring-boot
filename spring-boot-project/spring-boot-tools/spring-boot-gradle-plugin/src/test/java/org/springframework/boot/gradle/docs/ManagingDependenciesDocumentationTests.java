package org.springframework.boot.gradle.docs;

import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.boot.gradle.junit.GradleMultiDslExtension;
import org.springframework.boot.gradle.testkit.Dsl;
import org.springframework.boot.gradle.testkit.GradleBuild;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assumptions.assumingThat;

/**
 * Tests for the managing dependencies documentation.
 *


 */
@ExtendWith(GradleMultiDslExtension.class)
class ManagingDependenciesDocumentationTests {

	GradleBuild gradleBuild;

	@TestTemplate
	void dependenciesExampleEvaluatesSuccessfully() {
		this.gradleBuild.script("src/docs/gradle/managing-dependencies/dependencies").build();
	}

	@TestTemplate
	void customManagedVersions() {
		assertThat(this.gradleBuild.script("src/docs/gradle/managing-dependencies/custom-version").build("slf4jVersion")
				.getOutput()).contains("1.7.20");
	}

	@TestTemplate
	void dependencyManagementInIsolation() {
		assertThat(this.gradleBuild.script("src/docs/gradle/managing-dependencies/configure-bom")
				.build("dependencyManagement").getOutput()).contains("org.springframework.boot:spring-boot-starter ");
	}

	@TestTemplate
	void dependencyManagementInIsolationWithPluginsBlock() {
		assumingThat(this.gradleBuild.getDsl() == Dsl.KOTLIN,
				() -> assertThat(
						this.gradleBuild.script("src/docs/gradle/managing-dependencies/configure-bom-with-plugins")
								.build("dependencyManagement").getOutput())
										.contains("org.springframework.boot:spring-boot-starter TEST-SNAPSHOT"));
	}

	@TestTemplate
	void configurePlatform() {
		assertThat(this.gradleBuild.script("src/docs/gradle/managing-dependencies/configure-platform")
				.build("dependencies", "--configuration", "compileClasspath").getOutput())
						.contains("org.springframework.boot:spring-boot-starter ");
	}

	@TestTemplate
	void customManagedVersionsWithPlatform() {
		assertThat(this.gradleBuild.script("src/docs/gradle/managing-dependencies/custom-version-with-platform")
				.build("dependencies", "--configuration", "compileClasspath").getOutput()).contains("1.7.20");
	}

}
