package org.springframework.boot.build.cli;

import java.util.Collections;

import org.gradle.api.Task;
import org.gradle.api.tasks.TaskAction;

/**
 * A {@link Task} for creating a Homebrew formula manifest.
 *

 */
public class HomebrewFormula extends AbstractPackageManagerDefinitionTask {

	@TaskAction
	void createFormula() {
		createDescriptor(Collections.emptyMap());
	}

}
