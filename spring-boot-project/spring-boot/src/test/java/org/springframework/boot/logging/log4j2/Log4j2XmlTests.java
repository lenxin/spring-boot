package org.springframework.boot.logging.log4j2;

import java.io.IOException;
import java.io.InputStream;

import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.ConfigurationFactory;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import org.springframework.boot.logging.LoggingSystemProperties;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@code log4j2.xml}.
 *

 */
class Log4j2XmlTests {

	protected Configuration configuration;

	@AfterEach
	void stopConfiguration() {
		this.configuration.stop();
	}

	@Test
	void whenLogExceptionConversionWordIsNotConfiguredThenConsoleUsesDefault() {
		assertThat(consolePattern()).contains("%xwEx");
	}

	@Test
	void whenLogExceptionConversionWordIsSetThenConsoleUsesIt() {
		withSystemProperty(LoggingSystemProperties.EXCEPTION_CONVERSION_WORD, "custom",
				() -> assertThat(consolePattern()).contains("custom"));
	}

	@Test
	void whenLogLevelPatternIsNotConfiguredThenConsoleUsesDefault() {
		assertThat(consolePattern()).contains("%5p");
	}

	@Test
	void whenLogLevelPatternIsSetThenConsoleUsesIt() {
		withSystemProperty(LoggingSystemProperties.LOG_LEVEL_PATTERN, "custom",
				() -> assertThat(consolePattern()).contains("custom"));
	}

	@Test
	void whenLogLDateformatPatternIsNotConfiguredThenConsoleUsesDefault() {
		assertThat(consolePattern()).contains("yyyy-MM-dd HH:mm:ss.SSS");
	}

	@Test
	void whenLogDateformatPatternIsSetThenConsoleUsesIt() {
		withSystemProperty(LoggingSystemProperties.LOG_DATEFORMAT_PATTERN, "dd-MM-yyyy",
				() -> assertThat(consolePattern()).contains("dd-MM-yyyy"));
	}

	protected void withSystemProperty(String name, String value, Runnable action) {
		String previous = System.setProperty(name, value);
		action.run();
		if (previous == null) {
			System.clearProperty(name);
		}
		else {
			System.setProperty(name, previous);
		}
	}

	private String consolePattern() {
		prepareConfiguration();
		return ((PatternLayout) this.configuration.getAppender("Console").getLayout()).getConversionPattern();
	}

	protected void prepareConfiguration() {
		this.configuration = initializeConfiguration();
		this.configuration.start();
	}

	protected Configuration initializeConfiguration() {
		LoggerContext context = new LoggerContext("test");
		Configuration configuration = ConfigurationFactory.getInstance().getConfiguration(context,
				configurationSource());
		configuration.initialize();
		return configuration;
	}

	private ConfigurationSource configurationSource() {
		try (InputStream in = getClass().getResourceAsStream(getConfigFileName())) {
			return new ConfigurationSource(in);
		}
		catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}

	protected String getConfigFileName() {
		return "log4j2.xml";
	}

}
