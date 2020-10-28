package org.springframework.boot.logging.log4j2;

import java.io.File;

import org.apache.logging.log4j.core.layout.PatternLayout;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import org.springframework.boot.logging.LoggingSystemProperties;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@code log4j2-file.xml}.
 *

 */
class Log4j2FileXmlTests extends Log4j2XmlTests {

	@TempDir
	File temp;

	@Override
	@AfterEach
	void stopConfiguration() {
		super.stopConfiguration();
		System.clearProperty(LoggingSystemProperties.LOG_FILE);
	}

	@Test
	void whenLogExceptionConversionWordIsNotConfiguredThenFileAppenderUsesDefault() {
		assertThat(fileAppenderPattern()).contains("%xwEx");
	}

	@Test
	void whenLogExceptionConversionWordIsSetThenFileAppenderUsesIt() {
		withSystemProperty(LoggingSystemProperties.EXCEPTION_CONVERSION_WORD, "custom",
				() -> assertThat(fileAppenderPattern()).contains("custom"));
	}

	@Test
	void whenLogLevelPatternIsNotConfiguredThenFileAppenderUsesDefault() {
		assertThat(fileAppenderPattern()).contains("%5p");
	}

	@Test
	void whenLogLevelPatternIsSetThenFileAppenderUsesIt() {
		withSystemProperty(LoggingSystemProperties.LOG_LEVEL_PATTERN, "custom",
				() -> assertThat(fileAppenderPattern()).contains("custom"));
	}

	@Test
	void whenLogLDateformatPatternIsNotConfiguredThenFileAppenderUsesDefault() {
		assertThat(fileAppenderPattern()).contains("yyyy-MM-dd HH:mm:ss.SSS");
	}

	@Test
	void whenLogDateformatPatternIsSetThenFileAppenderUsesIt() {
		withSystemProperty(LoggingSystemProperties.LOG_DATEFORMAT_PATTERN, "dd-MM-yyyy",
				() -> assertThat(fileAppenderPattern()).contains("dd-MM-yyyy"));
	}

	@Override
	protected String getConfigFileName() {
		return "log4j2-file.xml";
	}

	@Override
	protected void prepareConfiguration() {
		System.setProperty(LoggingSystemProperties.LOG_FILE, new File(this.temp, "test.log").getAbsolutePath());
		super.prepareConfiguration();
	}

	private String fileAppenderPattern() {
		prepareConfiguration();
		return ((PatternLayout) this.configuration.getAppender("File").getLayout()).getConversionPattern();
	}

}
