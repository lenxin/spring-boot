package org.springframework.boot.gradle.tasks.bundling;

import org.springframework.boot.gradle.junit.GradleCompatibility;

/**
 * Integration tests for {@link BootJar}.
 *

 */
@GradleCompatibility(configurationCache = true)
class BootWarIntegrationTests extends AbstractBootArchiveIntegrationTests {

	BootWarIntegrationTests() {
		super("bootWar", "WEB-INF/lib/", "WEB-INF/classes/");
	}

}
