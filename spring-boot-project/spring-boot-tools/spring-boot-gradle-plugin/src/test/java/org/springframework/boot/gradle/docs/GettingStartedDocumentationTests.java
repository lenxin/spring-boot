package org.springframework.boot.gradle.docs;

import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.boot.gradle.junit.GradleMultiDslExtension;
import org.springframework.boot.gradle.testkit.GradleBuild;

/**
 * Tests for the getting started documentation.
 *


 */
@ExtendWith(GradleMultiDslExtension.class)
class GettingStartedDocumentationTests {

	GradleBuild gradleBuild;

	// NOTE: We can't run any `apply-plugin` tests because during a release the
	// jar won't be there

	@TestTemplate
	void typicalPluginsAppliesExceptedPlugins() {
		this.gradleBuild.script("src/docs/gradle/getting-started/typical-plugins").build("verify");
	}

}
