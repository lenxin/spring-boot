package org.springframework.boot.cli.command.grab;

import java.util.List;

import joptsimple.OptionSet;

import org.springframework.boot.cli.command.Command;
import org.springframework.boot.cli.command.OptionParsingCommand;
import org.springframework.boot.cli.command.options.CompilerOptionHandler;
import org.springframework.boot.cli.command.options.OptionSetGroovyCompilerConfiguration;
import org.springframework.boot.cli.command.options.SourceOptions;
import org.springframework.boot.cli.command.status.ExitStatus;
import org.springframework.boot.cli.compiler.GroovyCompiler;
import org.springframework.boot.cli.compiler.GroovyCompilerConfiguration;
import org.springframework.boot.cli.compiler.RepositoryConfigurationFactory;
import org.springframework.boot.cli.compiler.grape.RepositoryConfiguration;

/**
 * {@link Command} to grab the dependencies of one or more Groovy scripts.
 *

 * @since 1.0.0
 */
public class GrabCommand extends OptionParsingCommand {

	public GrabCommand() {
		super("grab", "Download a spring groovy script's dependencies to ./repository", new GrabOptionHandler());
	}

	private static final class GrabOptionHandler extends CompilerOptionHandler {

		@Override
		protected ExitStatus run(OptionSet options) throws Exception {
			SourceOptions sourceOptions = new SourceOptions(options);
			List<RepositoryConfiguration> repositoryConfiguration = RepositoryConfigurationFactory
					.createDefaultRepositoryConfiguration();
			GroovyCompilerConfiguration configuration = new OptionSetGroovyCompilerConfiguration(options, this,
					repositoryConfiguration);
			if (System.getProperty("grape.root") == null) {
				System.setProperty("grape.root", ".");
			}
			GroovyCompiler groovyCompiler = new GroovyCompiler(configuration);
			groovyCompiler.compile(sourceOptions.getSourcesArray());
			return ExitStatus.OK;
		}

	}

}
