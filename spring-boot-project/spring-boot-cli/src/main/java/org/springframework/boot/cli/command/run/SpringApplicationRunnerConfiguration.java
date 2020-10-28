package org.springframework.boot.cli.command.run;

import java.util.logging.Level;

import org.springframework.boot.cli.compiler.GroovyCompilerConfiguration;

/**
 * Configuration for the {@link SpringApplicationRunner}.
 *

 * @since 1.0.0
 */
public interface SpringApplicationRunnerConfiguration extends GroovyCompilerConfiguration {

	/**
	 * Returns {@code true} if the source file should be monitored for changes and
	 * automatically recompiled.
	 * @return {@code true} if file watching should be performed, otherwise {@code false}
	 */
	boolean isWatchForFileChanges();

	/**
	 * Returns the logging level to use.
	 * @return the logging level
	 */
	Level getLogLevel();

}
