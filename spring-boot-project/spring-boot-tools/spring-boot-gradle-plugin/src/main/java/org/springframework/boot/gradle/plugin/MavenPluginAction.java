package org.springframework.boot.gradle.plugin;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.tasks.Upload;

/**
 * {@link Action} that is executed in response to the
 * {@link org.gradle.api.plugins.MavenPlugin} being applied.
 *

 */
final class MavenPluginAction implements PluginApplicationAction {

	private final String uploadTaskName;

	MavenPluginAction(String uploadTaskName) {
		this.uploadTaskName = uploadTaskName;
	}

	@Override
	@SuppressWarnings("deprecation")
	public Class<? extends Plugin<? extends Project>> getPluginClass() {
		return org.gradle.api.plugins.MavenPlugin.class;
	}

	@Override
	public void execute(Project project) {
		project.getTasks().withType(Upload.class, (upload) -> {
			if (this.uploadTaskName.equals(upload.getName())) {
				project.afterEvaluate((evaluated) -> clearConfigurationMappings(upload));
			}
		});
	}

	@SuppressWarnings("deprecation")
	private void clearConfigurationMappings(Upload upload) {
		upload.getRepositories().withType(org.gradle.api.artifacts.maven.MavenResolver.class,
				(resolver) -> resolver.getPom().getScopeMappings().getMappings().clear());
	}

}
