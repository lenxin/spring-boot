package org.springframework.boot.gradle.tasks.bundling;

import org.gradle.api.Action;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.file.FileCollection;
import org.gradle.api.file.FileTreeElement;
import org.gradle.api.model.ReplacedBy;
import org.gradle.api.provider.Property;
import org.gradle.api.specs.Spec;
import org.gradle.api.tasks.Classpath;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Optional;

/**
 * A Spring Boot "fat" archive task.
 *

 * @since 2.0.0
 */
public interface BootArchive extends Task {

	/**
	 * Returns the fully-qualified name of the application's main class.
	 * @return the fully-qualified name of the application's main class
	 * @since 2.4.0
	 */
	@Input
	Property<String> getMainClass();

	/**
	 * Returns the fully-qualified main class name of the application.
	 * @return the fully-qualified name of the application's main class
	 * @deprecated since 2.4.0 in favor of {@link #getMainClass()}.
	 */
	@Deprecated
	@ReplacedBy("mainClass")
	String getMainClassName();

	/**
	 * Sets the fully-qualified main class name of the application.
	 * @param mainClassName the fully-qualified name of the application's main class
	 * @deprecated since 2.4.0 in favour of {@link #getMainClass} and
	 * {@link Property#set(Object)}
	 */
	@Deprecated
	void setMainClassName(String mainClassName);

	/**
	 * Adds Ant-style patterns that identify files that must be unpacked from the archive
	 * when it is launched.
	 * @param patterns the patterns
	 */
	void requiresUnpack(String... patterns);

	/**
	 * Adds a spec that identifies files that must be unpacked from the archive when it is
	 * launched.
	 * @param spec the spec
	 */
	void requiresUnpack(Spec<FileTreeElement> spec);

	/**
	 * Returns the {@link LaunchScriptConfiguration} that will control the script that is
	 * prepended to the archive.
	 * @return the launch script configuration, or {@code null} if the launch script has
	 * not been configured.
	 */
	@Input
	@Optional
	LaunchScriptConfiguration getLaunchScript();

	/**
	 * Configures the archive to have a prepended launch script.
	 */
	void launchScript();

	/**
	 * Configures the archive to have a prepended launch script, customizing its
	 * configuration using the given {@code action}.
	 * @param action the action to apply
	 */
	void launchScript(Action<LaunchScriptConfiguration> action);

	/**
	 * Returns the classpath that will be included in the archive.
	 * @return the classpath
	 */
	@Optional
	@Classpath
	FileCollection getClasspath();

	/**
	 * Adds files to the classpath to include in the archive. The given {@code classpath}
	 * is evaluated as per {@link Project#files(Object...)}.
	 * @param classpath the additions to the classpath
	 */
	void classpath(Object... classpath);

	/**
	 * Sets the classpath to include in the archive. The given {@code classpath} is
	 * evaluated as per {@link Project#files(Object...)}.
	 * @param classpath the classpath
	 * @since 2.0.7
	 */
	void setClasspath(Object classpath);

	/**
	 * Sets the classpath to include in the archive.
	 * @param classpath the classpath
	 * @since 2.0.7
	 */
	void setClasspath(FileCollection classpath);

	/**
	 * Returns {@code true} if the Devtools jar should be excluded, otherwise
	 * {@code false}.
	 * @return {@code true} if the Devtools jar should be excluded, or {@code false} if
	 * not
	 * @deprecated since 2.3.0 in favour of configuring a classpath that does not include
	 * development-only dependencies
	 */
	@Input
	@Deprecated
	boolean isExcludeDevtools();

	/**
	 * Sets whether or not the Devtools jar should be excluded.
	 * @param excludeDevtools {@code true} if the Devtools jar should be excluded, or
	 * {@code false} if not
	 * @deprecated since 2.3.0 in favour of configuring a classpath that does not include
	 * development-only dependencies
	 */
	@Deprecated
	void setExcludeDevtools(boolean excludeDevtools);

}
