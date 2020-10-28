package org.springframework.boot.cli.compiler;

import java.util.List;

import org.springframework.boot.cli.compiler.grape.RepositoryConfiguration;

/**
 * Configuration for the {@link GroovyCompiler}.
 *


 * @since 1.0.0
 */
public interface GroovyCompilerConfiguration {

	/**
	 * Constant to be used when there is no {@link #getClasspath() classpath}.
	 */
	String[] DEFAULT_CLASSPATH = { "." };

	/**
	 * Returns the scope in which the compiler operates.
	 * @return the scope of the compiler
	 */
	GroovyCompilerScope getScope();

	/**
	 * Returns if import declarations should be guessed.
	 * @return {@code true} if imports should be guessed, otherwise {@code false}
	 */
	boolean isGuessImports();

	/**
	 * Returns if jar dependencies should be guessed.
	 * @return {@code true} if dependencies should be guessed, otherwise {@code false}
	 */
	boolean isGuessDependencies();

	/**
	 * Returns true if auto-configuration transformations should be applied.
	 * @return {@code true} if auto-configuration transformations should be applied,
	 * otherwise {@code false}
	 */
	boolean isAutoconfigure();

	/**
	 * Returns the classpath for local resources.
	 * @return a path for local resources
	 */
	String[] getClasspath();

	/**
	 * Returns the configuration for the repositories that will be used by the compiler to
	 * resolve dependencies.
	 * @return the repository configurations
	 */
	List<RepositoryConfiguration> getRepositoryConfiguration();

	/**
	 * Returns if running in quiet mode.
	 * @return {@code true} if running in quiet mode
	 */
	boolean isQuiet();

}
