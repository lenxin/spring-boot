package org.springframework.boot.cli.util;

/**
 * Listener that can be attached to the {@link Log} to capture calls.
 *

 */
interface LogListener {

	void info(String message);

	void infoPrint(String message);

	void error(String message);

	void error(Exception ex);

}
