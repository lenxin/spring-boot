package org.springframework.boot.cli.command.run;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

import joptsimple.OptionSet;
import joptsimple.OptionSpec;

import org.springframework.boot.cli.command.Command;
import org.springframework.boot.cli.command.OptionParsingCommand;
import org.springframework.boot.cli.command.options.CompilerOptionHandler;
import org.springframework.boot.cli.command.options.OptionSetGroovyCompilerConfiguration;
import org.springframework.boot.cli.command.options.SourceOptions;
import org.springframework.boot.cli.command.status.ExitStatus;
import org.springframework.boot.cli.compiler.GroovyCompilerScope;
import org.springframework.boot.cli.compiler.RepositoryConfigurationFactory;
import org.springframework.boot.cli.compiler.grape.RepositoryConfiguration;

/**
 * {@link Command} to 'run' a groovy script or scripts.
 *



 * @since 1.0.0
 * @see SpringApplicationRunner
 */
public class RunCommand extends OptionParsingCommand {

	public RunCommand() {
		super("run", "Run a spring groovy script", new RunOptionHandler());
	}

	@Override
	public String getUsageHelp() {
		return "[options] <files> [--] [args]";
	}

	public void stop() {
		if (getHandler() != null) {
			((RunOptionHandler) getHandler()).stop();
		}
	}

	private static class RunOptionHandler extends CompilerOptionHandler {

		private final Object monitor = new Object();

		private OptionSpec<Void> watchOption;

		private OptionSpec<Void> verboseOption;

		private OptionSpec<Void> quietOption;

		private SpringApplicationRunner runner;

		@Override
		protected void doOptions() {
			this.watchOption = option("watch", "Watch the specified file for changes");
			this.verboseOption = option(Arrays.asList("verbose", "v"), "Verbose logging of dependency resolution");
			this.quietOption = option(Arrays.asList("quiet", "q"), "Quiet logging");
		}

		void stop() {
			synchronized (this.monitor) {
				if (this.runner != null) {
					this.runner.stop();
				}
				this.runner = null;
			}
		}

		@Override
		protected synchronized ExitStatus run(OptionSet options) throws Exception {
			synchronized (this.monitor) {
				if (this.runner != null) {
					throw new RuntimeException(
							"Already running. Please stop the current application before running another (use the 'stop' command).");
				}

				SourceOptions sourceOptions = new SourceOptions(options);

				List<RepositoryConfiguration> repositoryConfiguration = RepositoryConfigurationFactory
						.createDefaultRepositoryConfiguration();
				repositoryConfiguration.add(0,
						new RepositoryConfiguration("local", new File("repository").toURI(), true));

				SpringApplicationRunnerConfiguration configuration = new SpringApplicationRunnerConfigurationAdapter(
						options, this, repositoryConfiguration);

				this.runner = new SpringApplicationRunner(configuration, sourceOptions.getSourcesArray(),
						sourceOptions.getArgsArray());
				this.runner.compileAndRun();

				return ExitStatus.OK;
			}
		}

		/**
		 * Simple adapter class to present the {@link OptionSet} as a
		 * {@link SpringApplicationRunnerConfiguration}.
		 */
		private class SpringApplicationRunnerConfigurationAdapter extends OptionSetGroovyCompilerConfiguration
				implements SpringApplicationRunnerConfiguration {

			SpringApplicationRunnerConfigurationAdapter(OptionSet options, CompilerOptionHandler optionHandler,
					List<RepositoryConfiguration> repositoryConfiguration) {
				super(options, optionHandler, repositoryConfiguration);
			}

			@Override
			public GroovyCompilerScope getScope() {
				return GroovyCompilerScope.DEFAULT;
			}

			@Override
			public boolean isWatchForFileChanges() {
				return getOptions().has(RunOptionHandler.this.watchOption);
			}

			@Override
			public Level getLogLevel() {
				if (isQuiet()) {
					return Level.OFF;
				}
				if (getOptions().has(RunOptionHandler.this.verboseOption)) {
					return Level.FINEST;
				}
				return Level.INFO;
			}

			@Override
			public boolean isQuiet() {
				return getOptions().has(RunOptionHandler.this.quietOption);
			}

		}

	}

}
