package org.springframework.boot.cli.command.install;

import java.util.List;

import joptsimple.OptionSet;
import joptsimple.OptionSpec;

import org.springframework.boot.cli.command.Command;
import org.springframework.boot.cli.command.OptionParsingCommand;
import org.springframework.boot.cli.command.options.CompilerOptionHandler;
import org.springframework.boot.cli.command.status.ExitStatus;
import org.springframework.boot.cli.util.Log;

/**
 * {@link Command} to uninstall dependencies from the CLI's lib/ext directory.
 *


 * @since 1.2.0
 */
public class UninstallCommand extends OptionParsingCommand {

	public UninstallCommand() {
		super("uninstall", "Uninstall dependencies from the lib/ext directory", new UninstallOptionHandler());
	}

	@Override
	public String getUsageHelp() {
		return "[options] <coordinates>";
	}

	private static class UninstallOptionHandler extends CompilerOptionHandler {

		private OptionSpec<Void> allOption;

		@Override
		protected void doOptions() {
			this.allOption = option("all", "Uninstall all");
		}

		@Override
		@SuppressWarnings("unchecked")
		protected ExitStatus run(OptionSet options) throws Exception {
			List<String> args = (List<String>) options.nonOptionArguments();
			try {
				if (options.has(this.allOption)) {
					if (!args.isEmpty()) {
						throw new IllegalArgumentException("Please use --all without specifying any dependencies");
					}
					new Installer(options, this).uninstallAll();
				}
				if (args.isEmpty()) {
					throw new IllegalArgumentException(
							"Please specify at least one dependency, in the form group:artifact:version, to uninstall");
				}
				new Installer(options, this).uninstall(args);
			}
			catch (Exception ex) {
				String message = ex.getMessage();
				Log.error((message != null) ? message : ex.getClass().toString());
			}
			return ExitStatus.OK;
		}

	}

}
