package org.springframework.boot.gradle.plugin;

import io.spring.gradle.dependencymanagement.DependencyManagementPlugin;
import io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension;
import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;

/**
 * {@link Action} that is performed in response to the {@link DependencyManagementPlugin}
 * being applied.
 *

 */
final class DependencyManagementPluginAction implements PluginApplicationAction {

	@Override
	public void execute(Project project) {
		project.getExtensions().findByType(DependencyManagementExtension.class)
				.imports((importsHandler) -> importsHandler.mavenBom(SpringBootPlugin.BOM_COORDINATES));
	}

	@Override
	public Class<? extends Plugin<Project>> getPluginClass() {
		return DependencyManagementPlugin.class;
	}

}
