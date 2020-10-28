package org.springframework.boot.build.test;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.plugins.JavaPluginConvention;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.SourceSetContainer;
import org.gradle.api.tasks.testing.Test;
import org.gradle.language.base.plugins.LifecycleBasePlugin;
import org.gradle.plugins.ide.eclipse.EclipsePlugin;
import org.gradle.plugins.ide.eclipse.model.EclipseModel;

/**
 * A {@link Plugin} to configure integration testing support in a {@link Project}.
 *

 */
public class IntegrationTestPlugin implements Plugin<Project> {

	/**
	 * Name of the {@code intTest} task.
	 */
	public static String INT_TEST_TASK_NAME = "intTest";

	/**
	 * Name of the {@code intTest} source set.
	 */
	public static String INT_TEST_SOURCE_SET_NAME = "intTest";

	@Override
	public void apply(Project project) {
		project.getPlugins().withType(JavaPlugin.class, (javaPlugin) -> configureIntegrationTesting(project));
	}

	private void configureIntegrationTesting(Project project) {
		SourceSet intTestSourceSet = createSourceSet(project);
		Test intTest = createTestTask(project, intTestSourceSet);
		project.getTasks().getByName(LifecycleBasePlugin.CHECK_TASK_NAME).dependsOn(intTest);
		project.getPlugins().withType(EclipsePlugin.class, (eclipsePlugin) -> {
			EclipseModel eclipse = project.getExtensions().getByType(EclipseModel.class);
			eclipse.classpath((classpath) -> classpath.getPlusConfigurations().add(
					project.getConfigurations().getByName(intTestSourceSet.getRuntimeClasspathConfigurationName())));
		});
	}

	private SourceSet createSourceSet(Project project) {
		SourceSetContainer sourceSets = project.getConvention().getPlugin(JavaPluginConvention.class).getSourceSets();
		SourceSet intTestSourceSet = sourceSets.create(INT_TEST_SOURCE_SET_NAME);
		SourceSet main = sourceSets.getByName(SourceSet.MAIN_SOURCE_SET_NAME);
		intTestSourceSet.setCompileClasspath(intTestSourceSet.getCompileClasspath().plus(main.getOutput()));
		intTestSourceSet.setRuntimeClasspath(intTestSourceSet.getRuntimeClasspath().plus(main.getOutput()));
		return intTestSourceSet;
	}

	private Test createTestTask(Project project, SourceSet intTestSourceSet) {
		Test intTest = project.getTasks().create(INT_TEST_TASK_NAME, Test.class);
		intTest.setGroup(LifecycleBasePlugin.VERIFICATION_GROUP);
		intTest.setDescription("Runs integration tests.");
		intTest.setTestClassesDirs(intTestSourceSet.getOutput().getClassesDirs());
		intTest.setClasspath(intTestSourceSet.getRuntimeClasspath());
		intTest.shouldRunAfter(JavaPlugin.TEST_TASK_NAME);
		return intTest;
	}

}
