package org.springframework.boot.gradle.dsl;

import java.io.File;

import org.gradle.api.Action;
import org.gradle.api.Project;
import org.gradle.api.model.ReplacedBy;
import org.gradle.api.plugins.BasePlugin;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.plugins.JavaPluginConvention;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.TaskContainer;
import org.gradle.api.tasks.TaskProvider;
import org.gradle.jvm.tasks.Jar;

import org.springframework.boot.gradle.tasks.buildinfo.BuildInfo;
import org.springframework.boot.gradle.tasks.buildinfo.BuildInfoProperties;

/**
 * Entry point to Spring Boot's Gradle DSL.
 *


 * @since 2.0.0
 */
public class SpringBootExtension {

	private final Project project;

	private final Property<String> mainClass;

	/**
	 * Creates a new {@code SpringBootPluginExtension} that is associated with the given
	 * {@code project}.
	 * @param project the project
	 */
	public SpringBootExtension(Project project) {
		this.project = project;
		this.mainClass = this.project.getObjects().property(String.class);
	}

	/**
	 * Returns the fully-qualified name of the application's main class.
	 * @return the fully-qualified name of the application's main class
	 * @since 2.4.0
	 */
	public Property<String> getMainClass() {
		return this.mainClass;
	}

	/**
	 * Returns the fully-qualified main class name of the application.
	 * @return the fully-qualified name of the application's main class
	 * @deprecated since 2.4.0 in favor of {@link #getMainClass()}.
	 */
	@Deprecated
	@ReplacedBy("mainClass")
	public String getMainClassName() {
		return this.mainClass.getOrNull();
	}

	/**
	 * Sets the fully-qualified main class name of the application.
	 * @param mainClassName the fully-qualified name of the application's main class
	 * @deprecated since 2.4.0 in favour of {@link #getMainClass} and
	 * {@link Property#set(Object)}
	 */
	@Deprecated
	public void setMainClassName(String mainClassName) {
		this.mainClass.set(mainClassName);
	}

	/**
	 * Creates a new {@link BuildInfo} task named {@code bootBuildInfo} and configures the
	 * Java plugin's {@code classes} task to depend upon it.
	 * <p>
	 * By default, the task's destination dir will be a directory named {@code META-INF}
	 * beneath the main source set's resources output directory, and the task's project
	 * artifact will be the base name of the {@code bootWar} or {@code bootJar} task.
	 */
	public void buildInfo() {
		buildInfo(null);
	}

	/**
	 * Creates a new {@link BuildInfo} task named {@code bootBuildInfo} and configures the
	 * Java plugin's {@code classes} task to depend upon it. The task is passed to the
	 * given {@code configurer} for further configuration.
	 * <p>
	 * By default, the task's destination dir will be a directory named {@code META-INF}
	 * beneath the main source set's resources output directory, and the task's project
	 * artifact will be the base name of the {@code bootWar} or {@code bootJar} task.
	 * @param configurer the task configurer
	 */
	public void buildInfo(Action<BuildInfo> configurer) {
		TaskContainer tasks = this.project.getTasks();
		TaskProvider<BuildInfo> bootBuildInfo = tasks.register("bootBuildInfo", BuildInfo.class,
				this::configureBuildInfoTask);
		this.project.getPlugins().withType(JavaPlugin.class, (plugin) -> {
			tasks.named(JavaPlugin.CLASSES_TASK_NAME).configure((task) -> task.dependsOn(bootBuildInfo));
			this.project.afterEvaluate((evaluated) -> bootBuildInfo.configure((buildInfo) -> {
				BuildInfoProperties properties = buildInfo.getProperties();
				if (properties.getArtifact() == null) {
					properties.setArtifact(determineArtifactBaseName());
				}
			}));
		});
		if (configurer != null) {
			bootBuildInfo.configure(configurer);
		}
	}

	private void configureBuildInfoTask(BuildInfo task) {
		task.setGroup(BasePlugin.BUILD_GROUP);
		task.setDescription("Generates a META-INF/build-info.properties file.");
		task.getConventionMapping().map("destinationDir",
				() -> new File(determineMainSourceSetResourcesOutputDir(), "META-INF"));
	}

	private File determineMainSourceSetResourcesOutputDir() {
		return this.project.getConvention().getPlugin(JavaPluginConvention.class).getSourceSets()
				.getByName(SourceSet.MAIN_SOURCE_SET_NAME).getOutput().getResourcesDir();
	}

	private String determineArtifactBaseName() {
		Jar artifactTask = findArtifactTask();
		return (artifactTask != null) ? artifactTask.getArchiveBaseName().get() : null;
	}

	private Jar findArtifactTask() {
		Jar artifactTask = (Jar) this.project.getTasks().findByName("bootWar");
		if (artifactTask != null) {
			return artifactTask;
		}
		return (Jar) this.project.getTasks().findByName("bootJar");
	}

}
