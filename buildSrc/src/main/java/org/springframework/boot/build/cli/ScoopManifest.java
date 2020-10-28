package org.springframework.boot.build.cli;

import java.util.Collections;

import org.gradle.api.Task;
import org.gradle.api.tasks.TaskAction;

/**
 * A {@link Task} for creating a Scoop manifest.
 *

 */
public class ScoopManifest extends AbstractPackageManagerDefinitionTask {

	@TaskAction
	void createManifest() {
		String version = getProject().getVersion().toString();
		createDescriptor(Collections.singletonMap("scoopVersion", version.substring(0, version.lastIndexOf('.'))));
	}

}
