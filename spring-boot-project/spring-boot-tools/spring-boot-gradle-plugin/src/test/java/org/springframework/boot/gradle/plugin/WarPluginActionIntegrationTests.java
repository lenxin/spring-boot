package org.springframework.boot.gradle.plugin;

import java.io.File;

import org.gradle.testkit.runner.BuildResult;
import org.gradle.testkit.runner.TaskOutcome;
import org.junit.jupiter.api.TestTemplate;

import org.springframework.boot.gradle.junit.GradleCompatibility;
import org.springframework.boot.gradle.testkit.GradleBuild;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for {@link JavaPluginAction}.
 *

 */
@GradleCompatibility
class WarPluginActionIntegrationTests {

	GradleBuild gradleBuild;

	@TestTemplate
	void noBootWarTaskWithoutWarPluginApplied() {
		assertThat(this.gradleBuild.build("taskExists", "-PtaskName=bootWar").getOutput())
				.contains("bootWar exists = false");
	}

	@TestTemplate
	void applyingWarPluginCreatesBootWarTask() {
		assertThat(this.gradleBuild.build("taskExists", "-PtaskName=bootWar", "-PapplyWarPlugin").getOutput())
				.contains("bootWar exists = true");
	}

	@TestTemplate
	void assembleRunsBootWarAndWarIsSkipped() {
		BuildResult result = this.gradleBuild.build("assemble");
		assertThat(result.task(":bootWar").getOutcome()).isEqualTo(TaskOutcome.SUCCESS);
		assertThat(result.task(":war").getOutcome()).isEqualTo(TaskOutcome.SKIPPED);
	}

	@TestTemplate
	void warAndBootWarCanBothBeBuilt() {
		BuildResult result = this.gradleBuild.build("assemble");
		assertThat(result.task(":bootWar").getOutcome()).isEqualTo(TaskOutcome.SUCCESS);
		assertThat(result.task(":war").getOutcome()).isEqualTo(TaskOutcome.SUCCESS);
		File buildLibs = new File(this.gradleBuild.getProjectDir(), "build/libs");
		assertThat(buildLibs.listFiles()).containsExactlyInAnyOrder(
				new File(buildLibs, this.gradleBuild.getProjectDir().getName() + ".war"),
				new File(buildLibs, this.gradleBuild.getProjectDir().getName() + "-boot.war"));
	}

	@TestTemplate
	void errorMessageIsHelpfulWhenMainClassCannotBeResolved() {
		BuildResult result = this.gradleBuild.buildAndFail("build", "-PapplyWarPlugin");
		assertThat(result.task(":bootWar").getOutcome()).isEqualTo(TaskOutcome.FAILED);
		assertThat(result.getOutput()).contains("Main class name has not been configured and it could not be resolved");
	}

}
