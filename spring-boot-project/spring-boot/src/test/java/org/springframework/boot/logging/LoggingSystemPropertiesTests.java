package org.springframework.boot.logging;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.mock.env.MockEnvironment;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link LoggingSystemProperties}.
 *


 */
class LoggingSystemPropertiesTests {

	private Set<Object> systemPropertyNames;

	@BeforeEach
	void captureSystemPropertyNames() {
		System.getProperties().remove(LoggingSystemProperties.CONSOLE_LOG_CHARSET);
		System.getProperties().remove(LoggingSystemProperties.FILE_LOG_CHARSET);
		this.systemPropertyNames = new HashSet<>(System.getProperties().keySet());
	}

	@AfterEach
	void restoreSystemProperties() {
		System.getProperties().keySet().retainAll(this.systemPropertyNames);
	}

	@Test
	void pidIsSet() {
		new LoggingSystemProperties(new MockEnvironment()).apply(null);
		assertThat(System.getProperty(LoggingSystemProperties.PID_KEY)).isNotNull();
	}

	@Test
	void consoleLogPatternIsSet() {
		new LoggingSystemProperties(new MockEnvironment().withProperty("logging.pattern.console", "console pattern"))
				.apply(null);
		assertThat(System.getProperty(LoggingSystemProperties.CONSOLE_LOG_PATTERN)).isEqualTo("console pattern");
	}

	@Test
	void consoleCharsetWhenNoPropertyUsesUtf8() {
		new LoggingSystemProperties(new MockEnvironment()).apply(null);
		assertThat(System.getProperty(LoggingSystemProperties.CONSOLE_LOG_CHARSET)).isEqualTo("UTF-8");
	}

	@Test
	void consoleCharsetIsSet() {
		new LoggingSystemProperties(new MockEnvironment().withProperty("logging.charset.console", "UTF-16"))
				.apply(null);
		assertThat(System.getProperty(LoggingSystemProperties.CONSOLE_LOG_CHARSET)).isEqualTo("UTF-16");
	}

	@Test
	void fileLogPatternIsSet() {
		new LoggingSystemProperties(new MockEnvironment().withProperty("logging.pattern.file", "file pattern"))
				.apply(null);
		assertThat(System.getProperty(LoggingSystemProperties.FILE_LOG_PATTERN)).isEqualTo("file pattern");
	}

	@Test
	void fileCharsetWhenNoPropertyUsesUtf8() {
		new LoggingSystemProperties(new MockEnvironment()).apply(null);
		assertThat(System.getProperty(LoggingSystemProperties.FILE_LOG_CHARSET)).isEqualTo("UTF-8");
	}

	@Test
	void fileCharsetIsSet() {
		new LoggingSystemProperties(new MockEnvironment().withProperty("logging.charset.file", "UTF-16")).apply(null);
		assertThat(System.getProperty(LoggingSystemProperties.FILE_LOG_CHARSET)).isEqualTo("UTF-16");
	}

	@Test
	void consoleLogPatternCanReferencePid() {
		new LoggingSystemProperties(environment("logging.pattern.console", "${PID:unknown}")).apply(null);
		assertThat(System.getProperty(LoggingSystemProperties.CONSOLE_LOG_PATTERN)).matches("[0-9]+");
	}

	@Test
	void fileLogPatternCanReferencePid() {
		new LoggingSystemProperties(environment("logging.pattern.file", "${PID:unknown}")).apply(null);
		assertThat(System.getProperty(LoggingSystemProperties.FILE_LOG_PATTERN)).matches("[0-9]+");
	}

	@Test
	@SuppressWarnings("deprecation")
	void rollingFileNameIsSet() {
		new LoggingSystemProperties(
				new MockEnvironment().withProperty("logging.pattern.rolling-file-name", "rolling file pattern"))
						.apply(null);
		assertThat(System.getProperty(LoggingSystemProperties.ROLLING_FILE_NAME_PATTERN))
				.isEqualTo("rolling file pattern");
	}

	private Environment environment(String key, Object value) {
		StandardEnvironment environment = new StandardEnvironment();
		environment.getPropertySources().addLast(new MapPropertySource("test", Collections.singletonMap(key, value)));
		return environment;
	}

}
