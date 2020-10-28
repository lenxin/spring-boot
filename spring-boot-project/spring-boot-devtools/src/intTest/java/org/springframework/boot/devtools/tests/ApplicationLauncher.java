package org.springframework.boot.devtools.tests;

import java.io.File;

/**
 * Launches an application with DevTools.
 *


 */
public interface ApplicationLauncher {

	LaunchedApplication launchApplication(JvmLauncher javaLauncher, File serverPortFile) throws Exception;

	LaunchedApplication launchApplication(JvmLauncher jvmLauncher, File serverPortFile, String... additionalArgs)
			throws Exception;

}
