package org.springframework.boot.logging;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import org.springframework.boot.logging.LoggingSystem.NoOpLoggingSystem;
import org.springframework.boot.logging.logback.LogbackLoggingSystem;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

/**
 * Tests for {@link LoggingSystem}.
 *

 */
class LoggingSystemTests {

	@AfterEach
	void clearSystemProperty() {
		System.clearProperty(LoggingSystem.SYSTEM_PROPERTY);
	}

	@Test
	void logbackIsTheDefaultLoggingSystem() {
		assertThat(LoggingSystem.get(getClass().getClassLoader())).isInstanceOf(LogbackLoggingSystem.class);
	}

	@Test
	void loggingSystemCanBeDisabled() {
		System.setProperty(LoggingSystem.SYSTEM_PROPERTY, LoggingSystem.NONE);
		LoggingSystem loggingSystem = LoggingSystem.get(getClass().getClassLoader());
		assertThat(loggingSystem).isInstanceOf(NoOpLoggingSystem.class);
	}

	@Test
	void getLoggerConfigurationIsUnsupported() {
		assertThatExceptionOfType(UnsupportedOperationException.class)
				.isThrownBy(() -> new StubLoggingSystem().getLoggerConfiguration("test-logger-name"));
	}

	@Test
	void listLoggerConfigurationsIsUnsupported() {
		assertThatExceptionOfType(UnsupportedOperationException.class)
				.isThrownBy(() -> new StubLoggingSystem().getLoggerConfigurations());
	}

	private static final class StubLoggingSystem extends LoggingSystem {

		@Override
		public void beforeInitialize() {
			// Stub implementation
		}

		@Override
		public void setLogLevel(String loggerName, LogLevel level) {
			// Stub implementation
		}

	}

}
