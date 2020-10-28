package org.springframework.boot.cli.command.shell;

import org.springframework.boot.cli.command.CommandException;

/**
 * Exception used to stop the {@link Shell}.
 *

 * @since 1.0.0
 */
public class ShellExitException extends CommandException {

	private static final long serialVersionUID = 1L;

	public ShellExitException() {
		super(Option.RETHROW);
	}

}
