package org.springframework.boot.gradle.plugin;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.plugins.ExtraPropertiesExtension;
import org.jetbrains.kotlin.gradle.plugin.KotlinPluginWrapper;
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile;

/**
 * {@link PluginApplicationAction} that reacts to Kotlin's Gradle plugin being applied by
 * configuring a {@code kotlin.version} property to align the version used for dependency
 * management for Kotlin with the version of its plugin.
 *

 */
class KotlinPluginAction implements PluginApplicationAction {

	@Override
	public void execute(Project project) {
		String kotlinVersion = project.getPlugins().getPlugin(KotlinPluginWrapper.class).getKotlinPluginVersion();
		ExtraPropertiesExtension extraProperties = project.getExtensions().getExtraProperties();
		if (!extraProperties.has("kotlin.version")) {
			extraProperties.set("kotlin.version", kotlinVersion);
		}
		enableJavaParametersOption(project);
	}

	private void enableJavaParametersOption(Project project) {
		project.getTasks().withType(KotlinCompile.class,
				(compile) -> compile.getKotlinOptions().setJavaParameters(true));
	}

	@Override
	@SuppressWarnings("unchecked")
	public Class<? extends Plugin<? extends Project>> getPluginClass() {
		try {
			return (Class<? extends Plugin<? extends Project>>) Class
					.forName("org.jetbrains.kotlin.gradle.plugin.KotlinPluginWrapper");
		}
		catch (Throwable ex) {
			return null;
		}
	}

}
