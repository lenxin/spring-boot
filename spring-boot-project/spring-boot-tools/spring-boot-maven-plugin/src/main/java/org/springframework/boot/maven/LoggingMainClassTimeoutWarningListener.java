package org.springframework.boot.maven;

import java.util.function.Supplier;

import org.apache.maven.plugin.logging.Log;

import org.springframework.boot.loader.tools.Packager.MainClassTimeoutWarningListener;

/**
 * {@link MainClassTimeoutWarningListener} backed by a supplied Maven {@link Log}.
 *

 */
class LoggingMainClassTimeoutWarningListener implements MainClassTimeoutWarningListener {

	private final Supplier<Log> log;

	LoggingMainClassTimeoutWarningListener(Supplier<Log> log) {
		this.log = log;
	}

	@Override
	public void handleTimeoutWarning(long duration, String mainMethod) {
		this.log.get().warn("Searching for the main-class is taking some time, "
				+ "consider using the mainClass configuration parameter");
	}

}
