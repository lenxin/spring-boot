package org.springframework.boot.gradle.tasks.application;

import org.gradle.api.tasks.Optional;
import org.gradle.jvm.application.tasks.CreateStartScripts;

/**
 * Customization of {@link CreateStartScripts} that makes the {@link #getMainClassName()
 * main class name} optional.
 *

 * @since 2.0.0
 */
public class CreateBootStartScripts extends CreateStartScripts {

	@Override
	@Optional
	public String getMainClassName() {
		return super.getMainClassName();
	}

}
