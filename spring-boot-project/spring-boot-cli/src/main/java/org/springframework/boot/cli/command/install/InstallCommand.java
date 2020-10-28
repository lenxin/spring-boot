package org.springframework.boot.cli.command.install;

import java.util.List;

import joptsimple.OptionSet;

import org.springframework.boot.cli.command.Command;
import org.springframework.boot.cli.command.OptionParsingCommand;
import org.springframework.boot.cli.command.options.CompilerOptionHandler;
import org.springframework.boot.cli.command.status.ExitStatus;
import org.springframework.boot.cli.util.Log;
import org.springframework.util.Assert;

/**
 * {@link Command} to install additional dependencies into the CLI.
 *


 * @since 1.2.0
 */
public class InstallCommand extends OptionParsingCommand {

	public InstallCommand() {
		super("install", "Install dependencies to the lib/ext directory", new InstallOptionHandler());
	}

	@Override
	public String getUsageHelp() {
		return "[options] <coordinates>";
	}

	private static final class InstallOptionHandler extends CompilerOptionHandler {

		@Override
		@SuppressWarnings("unchecked")
		protected ExitStatus run(OptionSet options) throws Exception {
			List<String> args = (List<String>) options.nonOptionArguments();
			Assert.notEmpty(args,
					"Please specify at least one dependency, in the form group:artifact:version, to install");
			try {
				new Installer(options, this).install(args);
			}
			catch (Exception ex) {
				String message = ex.getMessage();
				Log.error((message != null) ? message : ex.getClass().toString());
			}
			return ExitStatus.OK;
		}

	}

}
