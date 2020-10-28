package org.springframework.boot.gradle.plugin;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.ConfigurationContainer;
import org.gradle.api.file.FileCollection;
import org.gradle.api.internal.artifacts.dsl.LazyPublishArtifact;
import org.gradle.api.plugins.BasePlugin;
import org.gradle.api.plugins.WarPlugin;
import org.gradle.api.provider.Provider;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.SourceSetContainer;
import org.gradle.api.tasks.TaskProvider;

import org.springframework.boot.gradle.tasks.bundling.BootWar;

/**
 * {@link Action} that is executed in response to the {@link WarPlugin} being applied.
 *


 */
class WarPluginAction implements PluginApplicationAction {

	private final SinglePublishedArtifact singlePublishedArtifact;

	WarPluginAction(SinglePublishedArtifact singlePublishedArtifact) {
		this.singlePublishedArtifact = singlePublishedArtifact;
	}

	@Override
	public Class<? extends Plugin<? extends Project>> getPluginClass() {
		return WarPlugin.class;
	}

	@Override
	public void execute(Project project) {
		disableWarTask(project);
		TaskProvider<BootWar> bootWar = configureBootWarTask(project);
		configureArtifactPublication(bootWar);
	}

	private void disableWarTask(Project project) {
		project.getTasks().named(WarPlugin.WAR_TASK_NAME).configure((war) -> war.setEnabled(false));
	}

	private TaskProvider<BootWar> configureBootWarTask(Project project) {
		Configuration developmentOnly = project.getConfigurations()
				.getByName(SpringBootPlugin.DEVELOPMENT_ONLY_CONFIGURATION_NAME);
		Configuration productionRuntimeClasspath = project.getConfigurations()
				.getByName(SpringBootPlugin.PRODUCTION_RUNTIME_CLASSPATH_NAME);
		FileCollection classpath = project.getConvention().getByType(SourceSetContainer.class)
				.getByName(SourceSet.MAIN_SOURCE_SET_NAME).getRuntimeClasspath()
				.minus(providedRuntimeConfiguration(project)).minus((developmentOnly.minus(productionRuntimeClasspath)))
				.filter(new JarTypeFileSpec());
		TaskProvider<ResolveMainClassName> resolveMainClassName = ResolveMainClassName
				.registerForTask(SpringBootPlugin.BOOT_WAR_TASK_NAME, project, classpath);
		TaskProvider<BootWar> bootWarProvider = project.getTasks().register(SpringBootPlugin.BOOT_WAR_TASK_NAME,
				BootWar.class, (bootWar) -> {
					bootWar.setGroup(BasePlugin.BUILD_GROUP);
					bootWar.setDescription("Assembles an executable war archive containing webapp"
							+ " content, and the main classes and their dependencies.");
					bootWar.providedClasspath(providedRuntimeConfiguration(project));
					bootWar.setClasspath(classpath);
					Provider<String> manifestStartClass = project
							.provider(() -> (String) bootWar.getManifest().getAttributes().get("Start-Class"));
					bootWar.getMainClass()
							.convention(resolveMainClassName.flatMap((resolver) -> manifestStartClass.isPresent()
									? manifestStartClass : resolveMainClassName.get().readMainClassName()));
				});
		bootWarProvider.map((bootWar) -> bootWar.getClasspath());
		return bootWarProvider;
	}

	private FileCollection providedRuntimeConfiguration(Project project) {
		ConfigurationContainer configurations = project.getConfigurations();
		return configurations.getByName(WarPlugin.PROVIDED_RUNTIME_CONFIGURATION_NAME);
	}

	private void configureArtifactPublication(TaskProvider<BootWar> bootWar) {
		LazyPublishArtifact artifact = new LazyPublishArtifact(bootWar);
		this.singlePublishedArtifact.addCandidate(artifact);
	}

}
