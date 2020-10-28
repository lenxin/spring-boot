package org.springframework.boot.cli.command;

/**
 * Exception used to when the help command is called without arguments.
 *

 * @since 1.0.0
 */
public class NoHelpCommandArgumentsException extends CommandException {

	private static final long serialVersionUID = 1L;

	public NoHelpCommandArgumentsException() {
		super(Option.SHOW_USAGE, Option.HIDE_MESSAGE);
	}

}
