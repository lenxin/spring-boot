package org.springframework.boot.logging;

import java.nio.file.Path;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.io.TempDir;

import org.springframework.util.StringUtils;

/**
 * Base for {@link LoggingSystem} tests.
 *



 */
public abstract class AbstractLoggingSystemTests {

	private static final String JAVA_IO_TMPDIR = "java.io.tmpdir";

	private String originalTempDirectory;

	@BeforeEach
	void configureTempDir(@TempDir Path temp) {
		this.originalTempDirectory = System.getProperty(JAVA_IO_TMPDIR);
		System.setProperty(JAVA_IO_TMPDIR, temp.toAbsolutePath().toString());
	}

	@AfterEach
	void reinstateTempDir() {
		System.setProperty(JAVA_IO_TMPDIR, this.originalTempDirectory);
	}

	@AfterEach
	void clear() {
		System.clearProperty(LoggingSystemProperties.LOG_FILE);
		System.clearProperty(LoggingSystemProperties.PID_KEY);
	}

	protected final String[] getSpringConfigLocations(AbstractLoggingSystem system) {
		return system.getSpringConfigLocations();
	}

	protected final LogFile getLogFile(String file, String path) {
		return getLogFile(file, path, true);
	}

	protected final LogFile getLogFile(String file, String path, boolean applyToSystemProperties) {
		LogFile logFile = new LogFile(file, path);
		if (applyToSystemProperties) {
			logFile.applyToSystemProperties();
		}
		return logFile;
	}

	protected final String tmpDir() {
		String path = StringUtils.cleanPath(System.getProperty(JAVA_IO_TMPDIR));
		if (path.endsWith("/")) {
			path = path.substring(0, path.length() - 1);
		}
		return path;
	}

}
