package org.springframework.boot.loader.tools;

import sun.misc.Signal;

/**
 * Utilities for working with signal handling.
 *

 * @since 1.1.0
 */
@SuppressWarnings("restriction")
public final class SignalUtils {

	private static final Signal SIG_INT = new Signal("INT");

	private SignalUtils() {
	}

	/**
	 * Handle {@literal INT} signals by calling the specified {@link Runnable}.
	 * @param runnable the runnable to call on SIGINT.
	 */
	public static void attachSignalHandler(Runnable runnable) {
		Signal.handle(SIG_INT, (signal) -> runnable.run());
	}

}
