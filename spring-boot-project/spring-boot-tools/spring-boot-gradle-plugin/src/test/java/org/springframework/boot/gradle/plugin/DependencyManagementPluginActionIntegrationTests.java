package org.springframework.boot.gradle.plugin;

import org.gradle.testkit.runner.TaskOutcome;
import org.junit.jupiter.api.TestTemplate;

import org.springframework.boot.gradle.junit.GradleCompatibility;
import org.springframework.boot.gradle.testkit.GradleBuild;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for the configuration applied by
 * {@link DependencyManagementPluginAction}.
 *

 */
@GradleCompatibility
class DependencyManagementPluginActionIntegrationTests {

	GradleBuild gradleBuild;

	@TestTemplate
	void noDependencyManagementIsAppliedByDefault() {
		assertThat(this.gradleBuild.build("doesNotHaveDependencyManagement").task(":doesNotHaveDependencyManagement")
				.getOutcome()).isEqualTo(TaskOutcome.SUCCESS);
	}

	@TestTemplate
	void bomIsImportedWhenDependencyManagementPluginIsApplied() {
		assertThat(this.gradleBuild.build("hasDependencyManagement", "-PapplyDependencyManagementPlugin")
				.task(":hasDependencyManagement").getOutcome()).isEqualTo(TaskOutcome.SUCCESS);
	}

}
