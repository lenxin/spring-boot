package org.springframework.boot.gradle.plugin;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;

/**
 * An {@link Action} to be executed on a {@link Project} in response to a particular type
 * of {@link Plugin} being applied.
 *

 */
interface PluginApplicationAction extends Action<Project> {

	/**
	 * The class of the {@code Plugin} that, when applied, will trigger the execution of
	 * this action. May return {@code null} if the plugin class is not on the classpath.
	 * @return the plugin class or {@code null}
	 */
	Class<? extends Plugin<? extends Project>> getPluginClass();

}
