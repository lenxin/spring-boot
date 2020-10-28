package org.springframework.boot.gradle.plugin;

import org.junit.jupiter.api.TestTemplate;

import org.springframework.boot.gradle.junit.GradleCompatibility;
import org.springframework.boot.gradle.testkit.GradleBuild;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for configuring a project to only use Spring Boot's dependency
 * management.
 *

 */
@GradleCompatibility
class OnlyDependencyManagementIntegrationTests {

	GradleBuild gradleBuild;

	@TestTemplate
	void dependencyManagementCanBeConfiguredUsingCoordinatesConstant() {
		assertThat(this.gradleBuild.build("dependencyManagement").getOutput())
				.contains("org.springframework.boot:spring-boot-starter ");
	}

}
