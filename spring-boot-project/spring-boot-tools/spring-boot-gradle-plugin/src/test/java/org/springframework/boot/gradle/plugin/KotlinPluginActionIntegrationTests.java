package org.springframework.boot.gradle.plugin;

import org.junit.jupiter.api.TestTemplate;

import org.springframework.boot.gradle.junit.GradleCompatibility;
import org.springframework.boot.gradle.testkit.GradleBuild;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for {@link KotlinPluginAction}.
 *

 */
@GradleCompatibility
class KotlinPluginActionIntegrationTests {

	GradleBuild gradleBuild;

	@TestTemplate
	void noKotlinVersionPropertyWithoutKotlinPlugin() {
		assertThat(this.gradleBuild.build("kotlinVersion").getOutput()).contains("Kotlin version: none");
	}

	@TestTemplate
	void kotlinVersionPropertyIsSet() {
		String output = this.gradleBuild.build("kotlinVersion", "dependencies", "--configuration", "compileClasspath")
				.getOutput();
		assertThat(output).containsPattern("Kotlin version: [0-9]\\.[0-9]\\.[0-9]+");
	}

	@TestTemplate
	void kotlinCompileTasksUseJavaParametersFlagByDefault() {
		assertThat(this.gradleBuild.build("kotlinCompileTasksJavaParameters").getOutput())
				.contains("compileKotlin java parameters: true").contains("compileTestKotlin java parameters: true");
	}

	@TestTemplate
	void kotlinCompileTasksCanOverrideDefaultJavaParametersFlag() {
		assertThat(this.gradleBuild.build("kotlinCompileTasksJavaParameters").getOutput())
				.contains("compileKotlin java parameters: false").contains("compileTestKotlin java parameters: false");
	}

}
