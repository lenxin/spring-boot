package org.springframework.boot.cli.util;

/**
 * Simple logger used by the CLI.
 *

 * @since 1.0.0
 */
public abstract class Log {

	private static LogListener listener;

	public static void info(String message) {
		System.out.println(message);
		if (listener != null) {
			listener.info(message);
		}
	}

	public static void infoPrint(String message) {
		System.out.print(message);
		if (listener != null) {
			listener.infoPrint(message);
		}
	}

	public static void error(String message) {
		System.err.println(message);
		if (listener != null) {
			listener.error(message);
		}
	}

	public static void error(Exception ex) {
		ex.printStackTrace(System.err);
		if (listener != null) {
			listener.error(ex);
		}
	}

	static void setListener(LogListener listener) {
		Log.listener = listener;
	}

}
