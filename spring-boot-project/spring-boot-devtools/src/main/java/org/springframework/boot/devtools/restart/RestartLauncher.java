package org.springframework.boot.devtools.restart;

import java.lang.reflect.Method;

/**
 * Thread used to launch a restarted application.
 *

 */
class RestartLauncher extends Thread {

	private final String mainClassName;

	private final String[] args;

	private Throwable error;

	RestartLauncher(ClassLoader classLoader, String mainClassName, String[] args,
			UncaughtExceptionHandler exceptionHandler) {
		this.mainClassName = mainClassName;
		this.args = args;
		setName("restartedMain");
		setUncaughtExceptionHandler(exceptionHandler);
		setDaemon(false);
		setContextClassLoader(classLoader);
	}

	@Override
	public void run() {
		try {
			Class<?> mainClass = Class.forName(this.mainClassName, false, getContextClassLoader());
			Method mainMethod = mainClass.getDeclaredMethod("main", String[].class);
			mainMethod.invoke(null, new Object[] { this.args });
		}
		catch (Throwable ex) {
			this.error = ex;
			getUncaughtExceptionHandler().uncaughtException(this, ex);
		}
	}

	Throwable getError() {
		return this.error;
	}

}
